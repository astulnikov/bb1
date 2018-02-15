package com.astulnikov.bb1mainunit.communication.metric

/**
 * @author aliaksei.stulnikau 29.01.18.
 */
interface Metric<out T : Any> {
    companion object {
        operator fun invoke(byteArray: ByteArray): Metric<*> {
            val usefulData = byteArray.copyOfRange(1, byteArray.size - 1)
            return when (byteArray[0]) {
                0.toByte() -> SpeedMetric(usefulData)
                1.toByte() -> SpeedMetric(usefulData)
                else -> SpeedMetric(usefulData)
            }
        }
    }

    fun getValue(): T
}
