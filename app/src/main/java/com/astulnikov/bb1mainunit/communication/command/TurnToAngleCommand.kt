package com.astulnikov.bb1mainunit.communication.command

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class TurnToAngleCommand(val angle: Int) : Command {

    constructor(data: ByteArray) : this(ByteBuffer.wrap(data.copyOf(4)).apply { order(ByteOrder.LITTLE_ENDIAN) }.int)

    override fun getBytes(): ByteArray {
        return byteArrayOf(TURN_TO_ANGLE_CODE.toByte()).plus(ByteBuffer.allocate(4).putInt(angle).array())
    }
}
