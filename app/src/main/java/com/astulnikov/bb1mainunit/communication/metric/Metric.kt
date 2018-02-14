package com.astulnikov.bb1mainunit.communication.metric

/**
 * @author aliaksei.stulnikau 29.01.18.
 */
interface Metric<out T : Any> {
    companion object {
        operator fun invoke(byteArray: ByteArray): Metric<*> {
            return when (byteArray[0]) {
                0.toByte() -> SpeedMetric()
                1.toByte() -> SpeedMetric()
                else -> SpeedMetric()
            }
        }
    }

    fun getValue(): T
}
