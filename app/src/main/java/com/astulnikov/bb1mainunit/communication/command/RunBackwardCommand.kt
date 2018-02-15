package com.astulnikov.bb1mainunit.communication.command

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class RunBackwardCommand : Command {

    override fun getBytes(): ByteArray {
        return byteArrayOf(1)
    }
}
