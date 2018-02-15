package com.astulnikov.bb1mainunit.communication.metric

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class SpeedMetric(val data: ByteArray) : Metric<Float> {

    private val value: Float = data[0].toFloat()

    override fun getValue(): Float {
        return value
    }
}
