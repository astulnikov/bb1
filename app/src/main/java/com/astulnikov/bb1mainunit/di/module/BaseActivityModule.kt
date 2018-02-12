package com.astulnikov.bb1mainunit.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.astulnikov.bb1mainunit.di.qualifier.ActivityFragmentManager
import com.astulnikov.bb1mainunit.di.scope.PerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
@Module
abstract class BaseActivityModule {

    @Binds
    @PerActivity
    abstract fun activityContext(activity: AppCompatActivity): Context

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        @ActivityFragmentManager
        fun activityFragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager

    }
}
