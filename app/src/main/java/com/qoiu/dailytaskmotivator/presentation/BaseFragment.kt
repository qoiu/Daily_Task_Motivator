package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.qoiu.dailytaskmotivator.ViewModelRequest

abstract class BaseFragment<T : ViewModel, B: ViewBinding> : Fragment() {


    protected lateinit var binding: B
    protected lateinit var viewModel: T
    protected abstract fun viewModelClass(): Class<T>
    protected abstract fun layoutResId(): Int
    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?)
    open fun update() {}
    /**
    *If true, then super onBackPress in Activity
     */
    open fun onBackPress() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ViewModelRequest).getViewModel(viewModelClass(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater,container)
        return binding.root
    }
}