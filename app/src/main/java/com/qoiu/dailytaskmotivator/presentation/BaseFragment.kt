package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.qoiu.dailytaskmotivator.ViewModelRequest

abstract class BaseFragment<T : ViewModel> : Fragment() {

    protected lateinit var viewModel: T
    protected abstract fun viewModelClass(): Class<T>
    protected abstract fun layoutResId(): Int
    open fun update() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ViewModelRequest).getViewModel(viewModelClass(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId(), container, false)
    }
}