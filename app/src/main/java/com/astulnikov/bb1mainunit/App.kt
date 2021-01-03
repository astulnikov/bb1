package com.astulnikov.bb1mainunit

import com.astulnikov.bb1mainunit.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
class App : DaggerApplication() {
    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) run { Timber.plant(Timber.DebugTree()) }
    }
}