package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.communication.command.RunForwardCommand
import com.astulnikov.bb1mainunit.communication.uart.SendCommandUartUseCase
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
@RunWith(MockitoJUnitRunner::class)
class SendCommandUseCaseTest {

    @Mock
    private lateinit var uartBB1Controller: BB1CommunicationController

    private lateinit var sendCommandUseCase: SendCommandUseCase

    @Before
    fun setUp() {
        sendCommandUseCase = SendCommandUartUseCase(uartBB1Controller)
    }

    @Test
    fun sendCommand_bytesDataTransferred() {
        val command = RunForwardCommand()
        given(uartBB1Controller.sendData(command.getBytes())).willReturn(Completable.complete())
        sendCommandUseCase.execute(command)

        verify(uartBB1Controller, Mockito.only()).sendData(command.getBytes())
    }
}
