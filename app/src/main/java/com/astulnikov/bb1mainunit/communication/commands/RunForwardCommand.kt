package com.astulnikov.bb1mainunit.communication.commands

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class RunForwardCommand : Command {

    override fun getBytes(): ByteArray {
        return byteArrayOf(0)
    }
}
