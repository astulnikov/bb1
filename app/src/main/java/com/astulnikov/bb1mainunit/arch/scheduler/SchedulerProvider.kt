package com.astulnikov.bb1mainunit.arch.scheduler

import io.reactivex.rxjava3.core.Scheduler


/**
 * @author aliaksei.stulnikau 20.01.18.
 */
interface SchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler

    fun single(): Scheduler
}
