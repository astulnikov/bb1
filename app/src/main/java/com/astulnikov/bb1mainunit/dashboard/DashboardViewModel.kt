package com.astulnikov.bb1mainunit.dashboard

import android.databinding.ObservableFloat
import android.databinding.ObservableInt
import com.astulnikov.bb1mainunit.arch.BaseViewModel
import com.astulnikov.bb1mainunit.arch.scheduler.SchedulerProvider
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.command.Command
import com.astulnikov.bb1mainunit.communication.command.RunBackwardCommand
import com.astulnikov.bb1mainunit.communication.command.RunForwardCommand
import com.astulnikov.bb1mainunit.communication.command.RunStopCommand
import com.astulnikov.bb1mainunit.communication.metric.RearDistanceMetric
import com.astulnikov.bb1mainunit.communication.metric.SpeedMetric
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 20.01.18.
 */
class DashboardViewModel @Inject constructor(schedulerProvider: SchedulerProvider,
                                             private val sendCommandUseCase: SendCommandUseCase,
                                             private val observeMetricsUseCase: ObserveMetricsUseCase) : BaseViewModel(schedulerProvider) {

    val speed = ObservableFloat()
    val rearDistance = ObservableInt()

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        compositeDisposable.add(observeMetricsUseCase.execute()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe({
                    when (it) {
                        is SpeedMetric -> speed.set(it.getValue())
                        is RearDistanceMetric -> rearDistance.set(it.getValue())
                    }
                }))
    }

    fun onRunForwardClicked() {
        sendCommand(RunForwardCommand())
    }

    fun onRunBackwardClicked() {
        sendCommand(RunBackwardCommand())
    }

    private fun sendCommand(command: Command) {
        sendCommandUseCase.execute(command)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe(
                        { Timber.d("$command command successfully transferred") },
                        { throwable -> Timber.w(throwable, "Error happened while sending $command") })
    }
}
