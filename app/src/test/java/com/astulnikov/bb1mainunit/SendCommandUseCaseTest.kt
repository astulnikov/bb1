package com.astulnikov.bb1mainunit

import com.astulnikov.bb1mainunit.communication.commands.Command
import com.astulnikov.bb1mainunit.communication.SendCommandUartUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.uart.UartBB1Controller
import io.reactivex.Completable
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
    private lateinit var uartBB1Controller: UartBB1Controller

    private lateinit var sendCommandUseCase: SendCommandUseCase

    @Before
    fun setUp() {
        sendCommandUseCase = SendCommandUartUseCase(uartBB1Controller)
    }

    @Test
    fun test() {
        val data = byteArrayOf(2, 4, 6, 8)

        given(uartBB1Controller.sendData(data)).willReturn(Completable.complete())
        val command = object : Command {
            override fun getBytes(): ByteArray {
                return data
            }
        }
        sendCommandUseCase.execute(command)
        verify(uartBB1Controller, Mockito.only()).sendData(data)
    }
}
