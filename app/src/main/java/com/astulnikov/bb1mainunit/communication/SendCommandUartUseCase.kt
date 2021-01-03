package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.communication.command.Command
import com.astulnikov.bb1mainunit.communication.uart.UartBB1Controller
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 09.02.18.
 */
class SendCommandUartUseCase @Inject constructor(private var uartBB1Controller: UartBB1Controller) : SendCommandUseCase {

    override fun execute(command: Command): Completable {
        return uartBB1Controller.sendData(command.getBytes())
    }
}
