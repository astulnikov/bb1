package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.communication.command.Command
import io.reactivex.rxjava3.core.Completable

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
interface SendCommandUseCase {
    fun execute(command: Command): Completable
}
