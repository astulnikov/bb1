package com.astulnikov.bb1mainunit.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 20.01.18.
 */
class DashboardViewModelFactory @Inject constructor(private val dashboardViewModel: Lazy<DashboardViewModel>) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == DashboardViewModel::class.java) {
            return dashboardViewModel.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }
}
