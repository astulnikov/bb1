package com.astulnikov.bb1mainunit.communication

import com.astulnikov.bb1mainunit.arch.scheduler.TestSchedulerProvider
import com.astulnikov.bb1mainunit.communication.metric.Metric
import com.astulnikov.bb1mainunit.communication.uart.UartBB1Controller
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.nio.ByteBuffer

/**
 * @author aliaksei.stulnikau 14.02.18.
 */
@RunWith(MockitoJUnitRunner::class)
class ObserveMetricsUseCaseTest {

    @Mock
    private lateinit var uartBB1Controller: UartBB1Controller

    private lateinit var observeMetricsUseCase: ObserveMetricsUseCase

    private val testScheduler: TestScheduler = TestScheduler()
    private val schedulerProvider = TestSchedulerProvider(testScheduler)

    @Before
    fun setUp() {
        observeMetricsUseCase = ObserveMetricsUartUseCase(uartBB1Controller)
    }

    @Test
    fun subscribe_SpeedMetricReceived() {
        val bytes = ByteBuffer.allocate(java.lang.Float.BYTES)
                .putFloat(100.5f)
                .array()
        val data = byteArrayOf(0).plus(bytes)
        val testMetric = Metric(data)

        given(uartBB1Controller.subscribeForData()).willReturn(Observable.just(data))

        val testObserver = observeMetricsUseCase.execute()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .test()
        testScheduler.triggerActions()

        testObserver
                .assertNoErrors()
                .assertValue({ value -> value.getValue() == testMetric.getValue() })
    }
}
