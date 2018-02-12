package com.astulnikov.bb1mainunit.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import com.astulnikov.bb1mainunit.dashboard.DashboardFragment
import com.astulnikov.bb1mainunit.dashboard.DashboardFragmentViewModel
import com.astulnikov.bb1mainunit.dashboard.DashboardViewModelFactory
import com.astulnikov.bb1mainunit.di.scope.PerFragment
import com.astulnikov.bb1mainunit.uart.RealUartBB1Controller
import com.astulnikov.bb1mainunit.uart.UartBB1Controller
import com.google.android.things.pio.PeripheralManagerService
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
@Module(includes = [BaseFragmentModule::class])
abstract class DashboardFragmentModule {

    @Binds
    @PerFragment
    abstract fun fragmentInstance(dashboardFragment: DashboardFragment): Fragment

    @Binds
    @PerFragment
    abstract fun bindDashboardViewModel(dashboardFragmentViewModel: DashboardFragmentViewModel): ViewModel

    @Binds
    @PerFragment
    abstract fun dashboardViewModelFactory(dashboardViewModelFactory: DashboardViewModelFactory): ViewModelProvider.NewInstanceFactory

    @Binds
    @PerFragment
    abstract fun bindUartBB1Controller(uartBB1Controller: RealUartBB1Controller): UartBB1Controller

    @Module
    companion object {
        @Provides
        @PerFragment
        fun providePeripheralManagerService(): PeripheralManagerService {
            return PeripheralManagerService()
        }
    }
}
