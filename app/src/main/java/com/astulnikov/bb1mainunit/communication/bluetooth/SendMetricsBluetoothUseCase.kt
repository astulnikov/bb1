package com.astulnikov.bb1mainunit.communication.bluetooth

import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.astulnikov.bb1mainunit.communication.SendMetricsUseCase
import com.astulnikov.bb1mainunit.communication.metric.Metric
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SendMetricsBluetoothUseCase @Inject constructor(
        @BluetoothQualifier private val bluetoothBB1Controller: BB1CommunicationController
) : SendMetricsUseCase {

    override fun execute(metric: Metric<Any>): Completable =
            bluetoothBB1Controller.sendData(metric.getBytes())
}