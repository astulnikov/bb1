package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.communication.commands.Command
import io.reactivex.Completable

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
interface SendCommandUseCase {
    fun execute(command: Command): Completable
}
