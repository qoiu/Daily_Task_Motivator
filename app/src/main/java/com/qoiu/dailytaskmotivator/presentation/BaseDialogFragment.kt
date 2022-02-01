package com.qoiu.dailytaskmotivator.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<Binding: ViewBinding>: DialogFragment() {
    private var _binding: Binding? = null
    private val binding get() = _binding!!

    protected abstract fun initBinding(): Binding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = initBinding()
        init(binding)
        return AlertDialog.Builder(requireActivity()).setView(binding.root).setCancelable(false).create()
    }

    abstract fun init(binding: Binding)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}