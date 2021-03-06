package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.arch.UseCase
import com.astulnikov.bb1mainunit.communication.metric.Metric
import io.reactivex.rxjava3.core.Observable

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
interface ObserveMetricsUseCase : UseCase {
    fun execute(): Observable<Metric<Any>>

    fun apply(metric: Metric<Int>)
}
