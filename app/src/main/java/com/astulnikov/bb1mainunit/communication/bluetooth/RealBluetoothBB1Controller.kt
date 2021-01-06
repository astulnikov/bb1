package com.astulnikov.bb1mainunit.communication.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.github.ivbaranov.rxbluetooth.BluetoothConnection
import com.github.ivbaranov.rxbluetooth.RxBluetooth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private val SPP_UUID = UUID.fromString("bbdeb0c8-ecbe-4779-9c5e-15ce43a34785")
private const val SERVER_NAME = "BB1_Server"
private const val END_LINE_SYMBOL = 'l'
private const val DISCOVERY_TIMEOUT = 300

class RealBluetoothBB1Controller @Inject constructor(
        private val appContext: Context
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

        if (!rxBluetooth.isBluetoothAvailable) {
            Timber.w("Bluetooth is not available")
        } else {
            if (!rxBluetooth.isBluetoothEnabled) {
                Timber.w("Bluetooth is disabled")
                Timber.w("Enabling... ")
                val result = rxBluetooth.enable()
                Timber.w("Result: $result")
            } else {
                Timber.i("Bluetooth is All good! Starting...")
                observeBluetoothStatus()
                createServer()
            }
        }
    }

    private fun enableDiscoverability() {
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERY_TIMEOUT)
        appContext.startActivity(discoverableIntent)
    }

    private fun observeBluetoothStatus() {
        Timber.i("observe Bluetooth Status")
        rxBluetoothSubscription.add(
                rxBluetooth.observeDiscovery()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe { action ->
                            Timber.i("Discovery Action $action")
                        }
        )
        rxBluetoothSubscription.add(
                rxBluetooth.observeScanMode()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe { mode ->
                            Timber.i("Scan mode $mode")
                        }
        )
        rxBluetoothSubscription.add(
                rxBluetooth.observeAclEvent()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe { event ->
                            Timber.i("Acl Event $event")
                        }
        )
        rxBluetoothSubscription.add(
                rxBluetooth.observeBondState()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe { state ->
                            Timber.i("Bound state $state")
                        }
        )
        rxBluetoothSubscription.add(
                rxBluetooth.observeConnectionState()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe { state ->
                            Timber.i("Connection state $state")
                        }
        )

//        Timber.i("enableDiscoverability")
//        enableDiscoverability()

    }

    private fun createServer() {
        Timber.i("Creating Server...")
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
                                        createServer()
                                    })
                        }, { throwable ->
                            Timber.w(throwable)
                        })
        )
    }

    override fun subscribeForData(): Observable<ByteArray> = receiveSubject.serialize()

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