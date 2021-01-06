package com.astulnikov.bb1mainunit.communication.uart

import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.google.android.things.pio.PeripheralManager
import com.google.android.things.pio.UartDevice
import com.google.android.things.pio.UartDeviceCallback
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


/**
 * @author aliaksei.stulnikau 29.01.18.
 */
class RealUartBB1Controller @Inject constructor(manager: PeripheralManager) : BB1CommunicationController {

    companion object {
        private const val UART_DEVICE_NAME = "UART0"
    }

    private val metricsSubject: Subject<ByteArray> = PublishSubject.create()
    private lateinit var device: UartDevice
    private lateinit var callback: UartDeviceCallback

//    init {
//        val deviceList = manager.uartDeviceList
//        if (deviceList.isEmpty()) {
//            Timber.i("No UART port available on this device.")
//        } else {
//            Timber.i("List of available devices: $deviceList")
//
//            device = manager.openUartDevice(UART_DEVICE_NAME)
//            device.setBaudrate(9600)
//            try {
//                callback = object : UartDeviceCallback {
//                    override fun onUartDeviceDataAvailable(uart: UartDevice?): Boolean {
//                        try {
//                            if (uart != null) {
//                                readUartBuffer(uart)
//                            }
//                        } catch (e: IOException) {
//                            Timber.w(e, "Unable to access UART device")
//                        }
//
//                        return true
//                    }
//
//                    override fun onUartDeviceError(uart: UartDevice?, error: Int) {
//                        Timber.w("$uart: Error event  $error")
//                    }
//                }
//                device.registerUartDeviceCallback(callback)
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//        }
//    }

    override fun subscribeForData(): Observable<ByteArray> {
        return metricsSubject.toSerialized()
    }

    override fun sendData(data: ByteArray): Completable {
        return Completable.fromAction {
//            val count = device.write(data, data.size)
//            Timber.d("Wrote $count bytes to peripheral")
        }
    }

    override fun release() {
        device.unregisterUartDeviceCallback(callback)
        device.close()
    }

    private fun readUartBuffer(uart: UartDevice) {
        // Maximum amount of data to read at one time
        val result = ArrayList<Byte>()
        val maxCount = 1024
        val buffer = ByteArray(maxCount)
        while (true) {
            val count = uart.read(buffer, buffer.size)
            if (count <= 0) break
            result.addAll(buffer.asList())
            Timber.d("Read $count bytes from peripheral")
            Timber.d("Read: ${buffer.contentToString()}")
        }
        metricsSubject.onNext(result.toByteArray())
    }
}
