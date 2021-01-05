package com.astulnikov.bb1mainunit.communication.command

/**
 * @author aliaksei.stulnikau 29.01.18.
 */
interface Command {

    companion object {
        operator fun invoke(byteArray: ByteArray): Command {
            val usefulData = byteArray.copyOfRange(1, byteArray.size)
            return when (byteArray[0]) {
                RUN_FORWARD_CODE.toByte() -> RunForwardCommand()
                RUN_BACKWARD_CODE.toByte() -> RunBackwardCommand()
                RUN_STOP_CODE.toByte() -> RunStopCommand()
                TURN_TO_ANGLE_CODE.toByte() -> TurnToAngleCommand(usefulData)
                else -> RunStopCommand()
            }
        }
    }

    fun getBytes(): ByteArray
}
