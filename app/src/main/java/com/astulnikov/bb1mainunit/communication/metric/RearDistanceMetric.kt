package com.astulnikov.bb1mainunit.communication.metric

import java.nio.ByteBuffer

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class RearDistanceMetric(val data: ByteArray) : Metric<Int> {
    private var value: Int = ByteBuffer.wrap(data.copyOf(4)).int

    override fun getValue(): Int {
        return value
    }

    override fun getBytes(): ByteArray = data
}
