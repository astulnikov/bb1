package com.astulnikov.bb1mainunit.communication.uart

import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.command.Command
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 09.02.18.
 */
class SendCommandUartUseCase @Inject constructor(
        @UartQualifier private var uartBB1Controller: BB1CommunicationController
) : SendCommandUseCase {

    override fun execute(command: Command): Completable {
        return uartBB1Controller.sendData(command.getBytes())
    }
}
