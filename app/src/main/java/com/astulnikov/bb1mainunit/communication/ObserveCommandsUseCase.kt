package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.arch.UseCase
import com.astulnikov.bb1mainunit.communication.command.Command
import io.reactivex.rxjava3.core.Observable

interface ObserveCommandsUseCase : UseCase {
    fun execute(): Observable<Command>
}
