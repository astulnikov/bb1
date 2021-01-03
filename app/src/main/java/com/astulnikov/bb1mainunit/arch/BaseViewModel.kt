package com.astulnikov.bb1mainunit.arch

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.astulnikov.bb1mainunit.arch.scheduler.SchedulerProvider
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

/**
 * @author aliaksei.stulnikau 20.01.18.
 *
 * Base app ViewModel class which wraps android arch ViewModel
 * @see ViewModel
 */
abstract class BaseViewModel(val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val actionSubject: PublishSubject<Action> = PublishSubject.create()

    protected fun subscribeForEvents(actionConsumer: Consumer<Action>): Disposable {
        return actionSubject.toSerialized().subscribe(actionConsumer)
    }

    protected fun getActionSubscription(): Subject<Action> {
        return actionSubject.toSerialized()
    }

    open fun start() {}

    open fun stop() {}

    interface Action {
        val action: Int
    }

    interface Event : Action {
        val data: Bundle
    }
}
