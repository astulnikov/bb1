package com.astulnikov.bb1mainunit.communication.bluetooth

import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.astulnikov.bb1mainunit.communication.ObserveCommandsUseCase
import com.astulnikov.bb1mainunit.communication.command.Command
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class ObserveCommandsBluetoothUseCase @Inject constructor(
        @BluetoothQualifier private var bluetoothBB1Controller: BB1CommunicationController
) : ObserveCommandsUseCase {

    override fun execute(): Observable<Command> =
            bluetoothBB1Controller.subscribeForData()
                    .map { Command(it) }
}
