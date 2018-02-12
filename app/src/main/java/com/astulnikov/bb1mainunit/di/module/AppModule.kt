package com.astulnikov.bb1mainunit.di.module

import android.app.Application
import com.astulnikov.bb1mainunit.App
import com.astulnikov.bb1mainunit.MainActivity
import com.astulnikov.bb1mainunit.di.scope.PerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
@Module(includes = [(AndroidSupportInjectionModule::class)])
abstract class AppModule {

    @Binds
    abstract fun application(app: App): Application

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

}
