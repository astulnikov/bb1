package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.communication.metric.Metric
import com.astulnikov.bb1mainunit.communication.uart.UartBB1Controller
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class ObserveMetricsUartUseCase @Inject constructor(private var uartBB1Controller: UartBB1Controller) : ObserveMetricsUseCase {

    override fun execute(): Observable<Metric<Any>> {
        return uartBB1Controller.subscribeForData()
                .map { data -> Metric(data) }
    }

    override fun apply(metric: Metric<Int>) {

    }
}
