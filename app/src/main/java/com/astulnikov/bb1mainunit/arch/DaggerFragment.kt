package com.astulnikov.bb1mainunit.arch

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.astulnikov.bb1mainunit.di.qualifier.ChildFragmentManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
abstract class DaggerFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    protected lateinit var activityContext: Context

    @field:[Inject ChildFragmentManager]
    lateinit var injectedChildFragmentManager: FragmentManager

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    protected fun replaceChildFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        injectedChildFragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .commit()
    }
}
