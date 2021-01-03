package com.astulnikov.bb1mainunit.arch

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.astulnikov.bb1mainunit.di.qualifier.ActivityFragmentManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
abstract class DaggerActivity : DaggerAppCompatActivity() {

    @Inject
    @ActivityFragmentManager
    lateinit var injectedFragmentManager: FragmentManager

    fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        val transaction = injectedFragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        transaction.commit()
    }
}
