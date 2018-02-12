package com.astulnikov.bb1mainunit.di.module

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.astulnikov.bb1mainunit.di.qualifier.ChildFragmentManager
import com.astulnikov.bb1mainunit.di.scope.PerFragment
import dagger.Module
import dagger.Provides

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
@Module
class BaseFragmentModule {

    @Provides
    @PerFragment
    @ChildFragmentManager
    fun childFragmentManager(fragment: Fragment): FragmentManager {
        return fragment.childFragmentManager
    }
}
