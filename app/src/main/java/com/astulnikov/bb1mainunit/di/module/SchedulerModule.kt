package com.astulnikov.bb1mainunit.di.module

import com.astulnikov.bb1mainunit.arch.scheduler.AndroidSchedulerProvider
import com.astulnikov.bb1mainunit.arch.scheduler.SchedulerProvider
import dagger.Binds
import dagger.Module

/**
 * @author aliaksei.stulnikau 20.01.18.
 */
@Module
abstract class SchedulerModule {

    @Binds
    abstract fun bindSchedulerProvider(schedulerProvider: AndroidSchedulerProvider): SchedulerProvider
}
