package com.qoiu.dailytaskmotivator.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<Binding: ViewBinding>: DialogFragment() {
    private var _binding: Binding? = null
    private val binding get() = _binding!!

    protected abstract fun initBinding(): Binding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = initBinding()
        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    abstract fun init(binding: Binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}