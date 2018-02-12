package com.astulnikov.bb1mainunit.di.module

import android.support.v7.app.AppCompatActivity
import com.astulnikov.bb1mainunit.MainActivity
import com.astulnikov.bb1mainunit.dashboard.DashboardFragment
import com.astulnikov.bb1mainunit.di.scope.PerActivity
import com.astulnikov.bb1mainunit.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
@Module(includes = [BaseActivityModule::class])
abstract class MainActivityModule {

    @Binds
    @PerActivity
    abstract fun activityInstance(activity: MainActivity): AppCompatActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [DashboardFragmentModule::class])
    abstract fun dashboardFragmentInjector(): DashboardFragment
}
