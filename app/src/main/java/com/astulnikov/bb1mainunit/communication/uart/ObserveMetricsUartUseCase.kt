package com.astulnikov.bb1mainunit.communication.uart

import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUseCase
import com.astulnikov.bb1mainunit.communication.metric.Metric
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class ObserveMetricsUartUseCase @Inject constructor(
        @UartQualifier private var uartBB1Controller: BB1CommunicationController
) : ObserveMetricsUseCase {

    override fun execute(): Observable<Metric<Any>> {
        return uartBB1Controller.subscribeForData()
                .map { data -> Metric(data) }
    }

    override fun apply(metric: Metric<Int>) {

    }
}
