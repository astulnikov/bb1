package com.astulnikov.bb1mainunit.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.astulnikov.bb1mainunit.BR
import dagger.android.support.DaggerFragment

/**
 * @author aliaksei.stulnikau 20.01.18.
 *
 * A fragment that is the View for a ViewModel in the MVVM DesignPattern
 *
 * @param <VM> The Type of the BaseViewModel
 */
abstract class MvvmFragment<out VM : BaseViewModel, out B : ViewDataBinding> : DaggerFragment() {

    private lateinit var viewModel: VM
    private lateinit var viewBinding: B

    protected abstract fun createViewModel(): VM

    protected abstract fun getLayoutId(): Int


    protected fun getViewModel(): VM {
        return viewModel
    }

    protected fun getViewBinding(): B {
        return viewBinding
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = createViewModel()
        return createAndBindContentView(inflater, container)
    }

    private fun createAndBindContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        viewBinding.setVariable(BR.viewModel, viewModel)
        return viewBinding.root
    }
}
