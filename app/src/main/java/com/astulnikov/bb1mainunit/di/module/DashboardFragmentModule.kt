package com.astulnikov.bb1mainunit.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUartUseCase
import com.astulnikov.bb1mainunit.communication.ObserveMetricsUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUartUseCase
import com.astulnikov.bb1mainunit.communication.SendCommandUseCase
import com.astulnikov.bb1mainunit.communication.uart.RealUartBB1Controller
import com.astulnikov.bb1mainunit.communication.uart.UartBB1Controller
import com.astulnikov.bb1mainunit.dashboard.DashboardFragment
import com.astulnikov.bb1mainunit.dashboard.DashboardViewModel
import com.astulnikov.bb1mainunit.dashboard.DashboardViewModelFactory
import com.astulnikov.bb1mainunit.di.scope.PerFragment
import com.google.android.things.pio.PeripheralManager
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
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    @PerFragment
    abstract fun dashboardViewModelFactory(dashboardViewModelFactory: DashboardViewModelFactory): ViewModelProvider.NewInstanceFactory

    @Binds
    @PerFragment
    abstract fun bindUartBB1Controller(uartBB1Controller: RealUartBB1Controller): UartBB1Controller

    @Binds
    @PerFragment
    abstract fun bindSendCommandUseCase(sendCommandUartUseCase: SendCommandUartUseCase): SendCommandUseCase

    @Binds
    @PerFragment
    abstract fun bindObserveMetricsUseCase(observeMetricsUartUseCase: ObserveMetricsUartUseCase): ObserveMetricsUseCase

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerFragment
        fun providePeripheralManagerService(): PeripheralManager {
            return PeripheralManager.getInstance()
        }
    }
}
