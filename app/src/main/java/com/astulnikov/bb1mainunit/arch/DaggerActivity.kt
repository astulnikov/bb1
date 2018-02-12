package com.astulnikov.bb1mainunit.arch

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.astulnikov.bb1mainunit.di.qualifier.ActivityFragmentManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
abstract class DaggerActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @field:[Inject ActivityFragmentManager]
    lateinit var injectedFragmentManager: FragmentManager

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        val transaction = injectedFragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}
