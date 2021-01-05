package com.astulnikov.bb1mainunit.communication.metric

import java.nio.ByteBuffer

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class SpeedMetric(val data: ByteArray) : Metric<Float> {

    private var value: Float = ByteBuffer.wrap(data.copyOf(4).apply { reverse() }).float

    override fun getValue(): Float {
        return value
    }

    override fun getBytes(): ByteArray = data
}
