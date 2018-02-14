package com.astulnikov.bb1mainunit.communication.uart

import com.google.android.things.pio.PeripheralManagerService
import com.google.android.things.pio.UartDevice
import com.google.android.things.pio.UartDeviceCallback
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject


/**
 * @author aliaksei.stulnikau 29.01.18.
 */
class RealUartBB1Controller @Inject constructor(manager: PeripheralManagerService) : UartBB1Controller {

    companion object {
        private const val UART_DEVICE_NAME = "device"
    }

    private val metricsSubject: Subject<ByteArray> = PublishSubject.create()
    private lateinit var device: UartDevice
    private lateinit var callback: UartDeviceCallback

    init {
        val deviceList = manager.uartDeviceList
        if (deviceList.isEmpty()) {
            Timber.i("No UART port available on this device.")
        } else {
            Timber.i("List of available devices: " + deviceList)

            device = manager.openUartDevice(UART_DEVICE_NAME)
            try {
                callback = object : UartDeviceCallback() {
                    override fun onUartDeviceDataAvailable(uart: UartDevice?): Boolean {
                        try {
                            if (uart != null) {
                                readUartBuffer(uart)
                            }
                        } catch (e: IOException) {
                            Timber.w("Unable to access UART device", e)
                        }

                        return true
                    }

                    override fun onUartDeviceError(uart: UartDevice?, error: Int) {
                        Timber.w("$uart: Error event  $error")
                    }
                }
                device.registerUartDeviceCallback(callback)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun subscribeForData(): Observable<ByteArray> {
        return metricsSubject.toSerialized()
    }

    override fun sendData(data: ByteArray): Completable {
        return Completable.fromAction({
            val count = device.write(data, data.size)
            Timber.d("Wrote $count bytes to peripheral")
        })
    }

    override fun release() {
        device.unregisterUartDeviceCallback(callback)
        device.close()
    }

    fun readUartBuffer(uart: UartDevice) {
        // Maximum amount of data to read at one time
        val result = ArrayList<Byte>()
        val maxCount = 1024
        val buffer = ByteArray(maxCount)
        var count: Int
        do {
            count = uart.read(buffer, buffer.size)
            result.addAll(buffer.asList())
            Timber.d("Read " + count + " bytes from peripheral")
        } while (count > 0)
        metricsSubject.onNext(result.toByteArray())
    }
}
