package com.astulnikov.bb1mainunit.dashboard

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import com.astulnikov.bb1mainunit.R
import com.astulnikov.bb1mainunit.arch.MvvmFragment
import com.astulnikov.bb1mainunit.arch.OnFragmentInteractionListener
import com.astulnikov.bb1mainunit.databinding.FragmentDashboardBinding
import javax.inject.Inject

/**
 * @author aliaksei.stulnikau 19.01.18.
 */
class DashboardFragment : MvvmFragment<DashboardViewModel, FragmentDashboardBinding>() {

    companion object {
        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var dashboardViewModelFactory: ViewModelProvider.NewInstanceFactory

    private var listener: OnFragmentInteractionListener? = null

    override fun createViewModel(): DashboardViewModel {
        return ViewModelProviders.of(this, dashboardViewModelFactory).get(DashboardViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onResume() {
        super.onResume()
        getViewModel().start()
    }

    override fun onPause() {
        getViewModel().stop()
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
