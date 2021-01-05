package com.astulnikov.bb1mainunit.communication.uart

import com.astulnikov.bb1mainunit.communication.BB1CommunicationController
import com.google.android.things.pio.PeripheralManager
import com.google.android.things.pio.UartDevice
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * @author aliaksei.stulnikau 12.02.18.
 */
@RunWith(MockitoJUnitRunner::class)
class UartBB1ControllerTest {

    @Mock
    private lateinit var manager: PeripheralManager

    @Mock
    private lateinit var device: UartDevice

    private lateinit var uartBB1Controller: BB1CommunicationController

    @Before
    fun setUp() {
        val deviceName = "device"
        given(manager.openUartDevice(anyString())).willReturn(device)
        given(manager.uartDeviceList).willReturn(Collections.singletonList(deviceName))
        uartBB1Controller = RealUartBB1Controller(manager)
    }

    @Test
    fun sendData_deviceReceiveData() {
        val data = byteArrayOf(2, 4, 6, 8)
        val observer: TestObserver<Void> = uartBB1Controller.sendData(data).test()
        observer.assertNoErrors()
        verify(device).write(data, data.size)
    }
}
