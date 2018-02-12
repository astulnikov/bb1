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
class DashboardFragment : MvvmFragment<DashboardFragmentViewModel, FragmentDashboardBinding>() {

    @Inject
    lateinit var dashboardViewModelFactory: ViewModelProvider.NewInstanceFactory

    private var listener: OnFragmentInteractionListener? = null

    override fun createViewModel(): DashboardFragmentViewModel {
        return ViewModelProviders.of(this, dashboardViewModelFactory).get(DashboardFragmentViewModel::class.java)
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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {

        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
