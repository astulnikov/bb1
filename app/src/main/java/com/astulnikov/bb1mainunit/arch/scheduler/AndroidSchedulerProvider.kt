package com.astulnikov.bb1mainunit.arch.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 20.01.18.
 *
 * SchedulerProvider implementation to use in Android environment
 */
class AndroidSchedulerProvider @Inject constructor() : SchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun single(): Scheduler {
        return Schedulers.single()
    }
}
