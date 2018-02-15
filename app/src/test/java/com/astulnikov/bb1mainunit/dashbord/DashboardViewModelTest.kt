package com.astulnikov.bb1mainunit.dashbord

import com.astulnikov.bb1mainunit.arch.scheduler.TestSchedulerProvider
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.command.RunBackwardCommand
import com.astulnikov.bb1mainunit.communication.command.RunForwardCommand
import com.astulnikov.bb1mainunit.communication.metric.RearDistanceMetric
import com.astulnikov.bb1mainunit.communication.metric.SpeedMetric
import com.astulnikov.bb1mainunit.dashboard.DashboardViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
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

/**
 * @author aliaksei.stulnikau 15.02.18.
 */
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    private fun <T> any(): T = Mockito.any<T>()

    @Mock
    private lateinit var sendCommandUseCase: SendCommandUseCase
    @Mock
    private lateinit var observeMetricsUseCase: ObserveMetricsUseCase

    private val testScheduler: TestScheduler = TestScheduler()
    private val schedulerProvider = TestSchedulerProvider(testScheduler)

    private lateinit var dashboardViewModel: DashboardViewModel

    @Before
    fun setUp() {
        dashboardViewModel = DashboardViewModel(schedulerProvider, sendCommandUseCase, observeMetricsUseCase)
    }

    @Test
    fun subscribeForMetrics_speed_propertyInitialised() {
        val data = byteArrayOf(4)
        val testMetric = SpeedMetric(data)
        given(observeMetricsUseCase.execute()).willReturn(Observable.just(testMetric))

        dashboardViewModel.start()
        testScheduler.triggerActions()

        assertEquals(testMetric.getValue(), dashboardViewModel.speed.get())
    }

    @Test
    fun subscribeForMetrics_rearDistance_propertyInitialised() {
        val data = byteArrayOf(4)
        val testMetric = RearDistanceMetric(data)
        given(observeMetricsUseCase.execute()).willReturn(Observable.just(testMetric))

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
