package com.astulnikov.bb1mainunit.arch.scheduler

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.TestScheduler


/**
 * @author aliaksei.stulnikau 14.02.18.
 */
class TestSchedulerProvider(private val testScheduler: TestScheduler) : SchedulerProvider {

    override fun computation(): Scheduler {
        return testScheduler
    }

    override fun io(): Scheduler {
        return testScheduler
    }

    override fun ui(): Scheduler {
        return testScheduler
    }

    override fun single(): Scheduler {
        return testScheduler
    }
}
