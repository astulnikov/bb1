package com.astulnikov.bb1mainunit.di.component

import android.app.Application
import com.astulnikov.bb1mainunit.App
import com.astulnikov.bb1mainunit.di.module.AppModule
import com.astulnikov.bb1mainunit.di.module.SchedulerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
@Singleton
@Component(modules = [AppModule::class, SchedulerModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    override fun inject(app: App)
}
