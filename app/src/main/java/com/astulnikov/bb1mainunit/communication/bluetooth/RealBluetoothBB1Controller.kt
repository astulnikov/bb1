package com.astulnikov.bb1mainunit.communication.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.content.Context
import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.github.ivbaranov.rxbluetooth.BluetoothConnection
import com.github.ivbaranov.rxbluetooth.RxBluetooth
import com.google.android.things.bluetooth.BluetoothClassFactory
import com.google.android.things.bluetooth.BluetoothConfigManager
import com.google.android.things.bluetooth.BluetoothProfileManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
private const val SERVER_NAME = "BB1_Server"
private const val END_LINE_SYMBOL = 'l'

class RealBluetoothBB1Controller @Inject constructor(
        appContext: Context
) : BB1CommunicationController {

    private val rxBluetooth: RxBluetooth = RxBluetooth(appContext)
    private lateinit var bluetoothConnection: BluetoothConnection
    private val rxBluetoothSubscription = CompositeDisposable()
    private val receiveSubject = BehaviorSubject.create<ByteArray>()

    init {
        rxBluetoothSubscription.add(
                rxBluetooth.observeBluetoothState()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe { state ->
                            Timber.i("Bluetooth state: $state")
                            if (state == BluetoothAdapter.STATE_ON) {
                                createServer()
                            }
                        }
        )

        Timber.i("Configure Bluetooth...")
        val manager = BluetoothConfigManager.getInstance()
        manager.bluetoothClass = BluetoothClassFactory.build(
                BluetoothClass.Service.INFORMATION,
                BluetoothClass.Device.COMPUTER_SERVER
        )
        manager.ioCapability = BluetoothConfigManager.IO_CAPABILITY_IO
        val enabledProfiles = BluetoothProfileManager.getInstance().enabledProfiles
        Timber.d("enabledProfiles $enabledProfiles")

        if (!rxBluetooth.isBluetoothAvailable) {
            Timber.w("Bluetooth is not available")
        } else {
            if (!rxBluetooth.isBluetoothEnabled) {
                Timber.w("Bluetooth is disabled")
                Timber.w("Enabling... ")
                val result = rxBluetooth.enable()
                Timber.w("Result: $result")
            } else {
                Timber.i("Bluetooth is All good!")
            }
        }
    }

    private fun createServer() {
        rxBluetoothSubscription.add(
                rxBluetooth.connectAsServer(SERVER_NAME, SPP_UUID)
                        .subscribe({ socket ->
                            Timber.i("Socket received")
                            bluetoothConnection = BluetoothConnection(socket)

                            val buffer = ArrayList<Byte>()
                            val delimiters = listOf(END_LINE_SYMBOL.toByte())
                            bluetoothConnection.observeByteStream()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe({ aByte ->
                                        Timber.i("aByte: $aByte")

                                        fun emit() {
                                            val byteArray = ByteArray(buffer.size)
                                            for (i in buffer.indices) {
                                                byteArray[i] = buffer[i]
                                            }
                                            receiveSubject.onNext(byteArray)
                                            buffer.clear()
                                        }

                                        var found = false
                                        for (delimiter in delimiters) {
                                            if (aByte == delimiter) {
                                                found = true
                                                break
                                            }
                                        }

                                        if (found) {
                                            emit()
                                        } else {
                                            buffer.add(aByte)
                                        }
                                    }, { throwable ->
                                        Timber.w(throwable)
                                        receiveSubject.onError(throwable)
                                    })
                        }, { throwable ->
                            Timber.w(throwable)
                        })
        )
    }

    override fun subscribeForData(): Observable<ByteArray> = receiveSubject.publish()

    override fun sendData(data: ByteArray): Completable =
            Completable.fromAction {
                Timber.i("sendData: $data")
                bluetoothConnection.send(data)
            }

    override fun release() {
        bluetoothConnection.closeConnection()
        rxBluetooth.cancelDiscovery()
        rxBluetoothSubscription.dispose()
    }
}