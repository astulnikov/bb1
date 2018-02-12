package com.astulnikov.bb1mainunit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.astulnikov.bb1mainunit.arch.DaggerActivity
import com.astulnikov.bb1mainunit.arch.OnFragmentInteractionListener
import com.astulnikov.bb1mainunit.dashboard.DashboardFragment
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
class MainActivity : DaggerActivity(), OnFragmentInteractionListener {

    @Inject
    lateinit var app: App

    override fun onFragmentInteraction(uri: String) {
        Log.d(localClassName, "Url: $uri")
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
