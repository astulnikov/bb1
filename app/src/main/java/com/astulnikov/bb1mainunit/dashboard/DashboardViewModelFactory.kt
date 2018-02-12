package com.astulnikov.bb1mainunit.dashboard

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 20.01.18.
 */
class DashboardViewModelFactory @Inject constructor(private val dashboardFragmentViewModel: Lazy<DashboardFragmentViewModel>) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == DashboardFragmentViewModel::class.java) {
            return dashboardFragmentViewModel.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass)
    }
}
