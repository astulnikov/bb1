package com.astulnikov.bb1mainunit.communication.metric

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class RearDistanceMetric(val data: ByteArray) : Metric<Int> {

    private val value: Int = data[0].toInt()

    override fun getValue(): Int {
        return value
    }
}
