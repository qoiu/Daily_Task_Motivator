package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    private var _binding: Binding? = null
    private val binding get() = _binding!!
    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): Binding
    open fun update() {}
    abstract fun init(binding: Binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(binding)
    }

    /**
     *If true, then super onBackPress in Activity
     */
    open fun onBackPress() = true


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