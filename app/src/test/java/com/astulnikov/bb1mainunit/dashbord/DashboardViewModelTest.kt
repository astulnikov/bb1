package com.astulnikov.bb1mainunit.dashbord

import com.astulnikov.bb1mainunit.arch.scheduler.TestSchedulerProvider
import com.astulnikov.bb1mainunit.communication.ObserveCommandsUseCase
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.command.RunBackwardCommand
import com.astulnikov.bb1mainunit.communication.command.RunForwardCommand
import com.astulnikov.bb1mainunit.communication.metric.Metric
import com.astulnikov.bb1mainunit.communication.metric.REAR_DISTANCE_METRIC_CODE
import com.astulnikov.bb1mainunit.communication.metric.SPEED_METRIC_CODE
import com.astulnikov.bb1mainunit.dashboard.DashboardViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.only
import org.mockito.junit.MockitoJUnitRunner
import java.nio.ByteBuffer

/**
 * @author aliaksei.stulnikau 15.02.18.
 */
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {

    private fun <T> any(type: Class<T>): T = Mockito.any(type)
    private fun <T> any(): T = Mockito.any()

    @Mock
    private lateinit var sendCommandUseCase: SendCommandUseCase

    @Mock
    private lateinit var observeMetricsUseCase: ObserveMetricsUseCase

    @Mock
    private lateinit var observeCommandsUseCase: ObserveCommandsUseCase

    private val testScheduler: TestScheduler = TestScheduler()
    private val schedulerProvider = TestSchedulerProvider(testScheduler)

    private lateinit var dashboardViewModel: DashboardViewModel

    @Before
    fun setUp() {
        dashboardViewModel = DashboardViewModel(schedulerProvider, sendCommandUseCase, observeMetricsUseCase, observeCommandsUseCase)
    }

    @Test
    fun subscribeForMetrics_speed_propertyInitialised() {
        val bytes = ByteBuffer.allocate(java.lang.Float.BYTES)
                .putFloat(100.5f)
                .array()
        val data = byteArrayOf(SPEED_METRIC_CODE.toByte()).plus(bytes)
        val testMetric = Metric(data)
        val testCommand = RunForwardCommand()

        given(observeMetricsUseCase.execute()).willReturn(Observable.just(testMetric))
        given(observeCommandsUseCase.execute()).willReturn(Observable.just(testCommand))

        dashboardViewModel.start()
        testScheduler.triggerActions()

        assertEquals(testMetric.getValue(), dashboardViewModel.speed.get())
    }

    @Test
    fun subscribeForMetrics_rearDistance_propertyInitialised() {
        val bytes = ByteBuffer.allocate(Integer.BYTES)
                .putInt(118)
                .array()
        val data = byteArrayOf(REAR_DISTANCE_METRIC_CODE.toByte()).plus(bytes)
        val testMetric = Metric(data)
        val testCommand = RunForwardCommand()

        given(observeMetricsUseCase.execute()).willReturn(Observable.just(testMetric))
        given(observeCommandsUseCase.execute()).willReturn(Observable.just(testCommand))

        dashboardViewModel.start()
        testScheduler.triggerActions()

        assertEquals(testMetric.getValue(), dashboardViewModel.rearDistance.get())
    }

    @Test
    fun onForwardClick_CommandSend() {
        given(sendCommandUseCase.execute(any())).willReturn(Completable.complete())

        dashboardViewModel.onRunForwardClicked()
        testScheduler.triggerActions()

        verify(sendCommandUseCase, only()).execute(any(RunForwardCommand::class.java))
    }


    @Test
    fun onBackwardClick_CommandSend() {
        given(sendCommandUseCase.execute(any())).willReturn(Completable.complete())

        dashboardViewModel.onRunBackwardClicked()
        testScheduler.triggerActions()

        verify(sendCommandUseCase, only()).execute(any(RunBackwardCommand::class.java))
    }
}
