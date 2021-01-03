package com.astulnikov.bb1mainunit

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.astulnikov.bb1mainunit.arch.DaggerActivity
import com.astulnikov.bb1mainunit.arch.OnFragmentInteractionListener
import com.astulnikov.bb1mainunit.dashboard.DashboardFragment
import timber.log.Timber

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
class MainActivity : DaggerActivity(), OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: String) {
        Timber.d("Url: $uri")
        title = uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceMainContent(DashboardFragment.newInstance())
    }

    private fun replaceMainContent(fragment: Fragment) {
        replaceFragment(R.id.main_container, fragment)
    }
}
