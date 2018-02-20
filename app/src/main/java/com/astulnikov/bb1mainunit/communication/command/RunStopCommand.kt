package com.astulnikov.bb1mainunit.communication.command

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class RunStopCommand : Command {

    override fun getBytes(): ByteArray {
        return byteArrayOf(RUN_STOP_CODE.toByte())
    }
}
