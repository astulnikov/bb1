package com.astulnikov.bb1mainunit.uart

import io.reactivex.Completable
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 09.02.18.
 */
class SendComandUseCase @Inject constructor(private var uartBB1Controller: UartBB1Controller) : UseCase {


    fun execute(command: Command): Completable {
        return uartBB1Controller.sendData(command.getBytes())
    }
}
