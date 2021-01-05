package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.communication.metric.Metric
import io.reactivex.rxjava3.core.Completable

interface SendMetricsUseCase {
    fun execute(metric: Metric<Any>): Completable
}
