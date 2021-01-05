package com.astulnikov.bb1mainunit.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astulnikov.bb1mainunit.communication.*
import com.astulnikov.bb1mainunit.communication.bluetooth.BluetoothQualifier
import com.astulnikov.bb1mainunit.communication.bluetooth.ObserveCommandsBluetoothUseCase
import com.astulnikov.bb1mainunit.communication.bluetooth.RealBluetoothBB1Controller
import com.astulnikov.bb1mainunit.communication.bluetooth.SendMetricsBluetoothUseCase
import com.astulnikov.bb1mainunit.communication.uart.ObserveMetricsUartUseCase
import com.astulnikov.bb1mainunit.communication.uart.RealUartBB1Controller
import com.astulnikov.bb1mainunit.communication.uart.SendCommandUartUseCase
import com.astulnikov.bb1mainunit.communication.uart.UartQualifier
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
    @UartQualifier
    abstract fun bindUartBB1Controller(uartBB1Controller: RealUartBB1Controller): BB1CommunicationController

    @Binds
    @PerFragment
    abstract fun bindSendCommandUseCase(sendCommandUartUseCase: SendCommandUartUseCase): SendCommandUseCase

    @Binds
    @PerFragment
    abstract fun bindObserveMetricsUseCase(observeMetricsUartUseCase: ObserveMetricsUartUseCase): ObserveMetricsUseCase

    @Binds
    @PerFragment
    @BluetoothQualifier
    abstract fun bindBluetoothBB1Controller(bluetoothBB1Controller: RealBluetoothBB1Controller): BB1CommunicationController

    @Binds
    @PerFragment
    abstract fun bindObserveCommandsUseCase(observeCommandsUseCase: ObserveCommandsBluetoothUseCase): ObserveCommandsUseCase

    @Binds
    @PerFragment
    abstract fun bindSendMetricsUseCase(sendMetricsUseCase: SendMetricsBluetoothUseCase): SendMetricsUseCase

    companion object {

        @Provides
        @PerFragment
        fun providePeripheralManagerService(): PeripheralManager {
            return PeripheralManager.getInstance()
        }
    }
}
