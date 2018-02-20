package com.astulnikov.bb1mainunit.communication.metric

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class RawMetric(val data: ByteArray) : Metric<ByteArray> {


    override fun getValue(): ByteArray {
        return data
    }
}
