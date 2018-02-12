package com.astulnikov.bb1mainunit.arch

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.astulnikov.bb1mainunit.arch.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * @author aliaksei.stulnikau 20.01.18.
 *
 * Base app ViewModel class which wraps android arch ViewModel
 * @see ViewModel
 */
abstract class BaseViewModel(val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val actionSubject: PublishSubject<Action> = PublishSubject.create()

    fun subscribeForEvents(actionConsumer: Consumer<Action>): Disposable {
        return actionSubject.toSerialized().subscribe(actionConsumer)
    }

    protected fun getActionSubscription(): Subject<Action> {
        return actionSubject.toSerialized()
    }

    fun start() {}

    fun stop() {}

    interface Action {
        val action: Int
    }

    interface Event : Action {
        val data: Bundle
    }
}
