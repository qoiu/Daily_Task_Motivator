package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.qoiu.dailytaskmotivator.ViewModelRequest

abstract class BaseFragment<Model : ViewModel, Binding : ViewBinding> : Fragment() {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!
    protected lateinit var viewModel: Model
    protected abstract fun viewModelClass(): Class<Model>
    protected abstract fun layoutResId(): Int
    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): Binding
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
        _binding = initBinding(inflater, container)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}