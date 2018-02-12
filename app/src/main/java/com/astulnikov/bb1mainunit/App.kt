package com.astulnikov.bb1mainunit

import android.app.Activity
import android.app.Application
import com.astulnikov.bb1mainunit.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
class App : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    lateinit var addComponent: AndroidInjector<App>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().create(this).inject(this)

        if (BuildConfig.DEBUG) run { Timber.plant(Timber.DebugTree()) }
    }
}
