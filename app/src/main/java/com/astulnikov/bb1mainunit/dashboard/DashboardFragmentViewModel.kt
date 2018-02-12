package com.astulnikov.bb1mainunit.dashboard

import com.astulnikov.bb1mainunit.arch.BaseViewModel
import com.astulnikov.bb1mainunit.arch.scheduler.SchedulerProvider
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 20.01.18.
 */
class DashboardFragmentViewModel @Inject constructor(schedulerProvider: SchedulerProvider) : BaseViewModel(schedulerProvider) {

    fun getName(): String {
        return "DashBoard ViewModel"
    }

    fun getSpeed(): Int {
        return 50
    }

    fun getRearDistance(): Int {
        return 88
    }
}