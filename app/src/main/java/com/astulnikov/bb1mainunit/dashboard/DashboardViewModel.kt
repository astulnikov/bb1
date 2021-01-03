package com.astulnikov.bb1mainunit.dashboard

import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import com.astulnikov.bb1mainunit.arch.BaseViewModel
import com.astulnikov.bb1mainunit.arch.scheduler.SchedulerProvider
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.command.Command
import com.astulnikov.bb1mainunit.communication.command.RunBackwardCommand
import com.astulnikov.bb1mainunit.communication.command.RunForwardCommand
import com.astulnikov.bb1mainunit.communication.command.RunStopCommand
import com.astulnikov.bb1mainunit.communication.metric.HeadingMetric
import com.astulnikov.bb1mainunit.communication.metric.RearDistanceMetric
import com.astulnikov.bb1mainunit.communication.metric.SpeedMetric
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author aliaksei.stulnikau 20.01.18.
 */
class DashboardViewModel @Inject constructor(
        schedulerProvider: SchedulerProvider,
        private val sendCommandUseCase: SendCommandUseCase,
        private val observeMetricsUseCase: ObserveMetricsUseCase
) : BaseViewModel(schedulerProvider) {

    val speed = ObservableFloat()
    val heading = ObservableFloat()
    val rearDistance = ObservableInt()

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        compositeDisposable.add(observeMetricsUseCase.execute()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe {
                    when (it) {
                        is SpeedMetric -> speed.set(it.getValue())
                        is HeadingMetric -> heading.set(it.getValue())
                        is RearDistanceMetric -> {
                            rearDistance.set(it.getValue())
                            observeMetricsUseCase.apply(it)
                        }
                    }
                    Timber.i("Metrics ${it::class.simpleName} received:  ${it.getValue()}")
                })

        Observable.interval(2000L, 2000L, TimeUnit.MILLISECONDS)
                .subscribe { time ->
                    Timber.d("Time: $time")
                    if (Random.nextBoolean()) {
                        onRunForwardClicked()
                    } else {
                        onStopClicked()
                    }
                }
    }

    fun onRunForwardClicked() {
        Timber.d("onRunForwardClicked")
        sendCommand(RunForwardCommand())
    }

    fun onRunBackwardClicked() {
        Timber.d("onRunBackwardClicked")
        sendCommand(RunBackwardCommand())
    }

    fun onStopClicked() {
        Timber.d("onStopClicked")
        sendCommand(RunStopCommand())
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
