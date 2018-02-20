package com.astulnikov.bb1mainunit.communication.metric

/**
 * @author aliaksei.stulnikau 29.01.18.
 */
interface Metric<out T : Any> {
    companion object {
        operator fun invoke(byteArray: ByteArray): Metric<*> {
            val usefulData = byteArray.copyOfRange(1, byteArray.size)
            return when (byteArray[0]) {
                SPEED_METRIC_CODE.toByte() -> SpeedMetric(usefulData)
                REAR_DISTANCE_METRIC_CODE.toByte() -> RearDistanceMetric(usefulData)
                else -> RawMetric(byteArray)
            }
        }
    }

    fun getValue(): T
}
