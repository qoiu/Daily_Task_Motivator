package com.qoiu.dailytaskmotivator.presentation.task

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.databinding.ColorPickerBinding
import com.qoiu.dailytaskmotivator.presentation.ColorPicker

class ColorPickerDialog(
    private val picked: (int: String) -> Unit,
    private val defaultColor: String =  "#ffffffff"
) : DialogFragment() {
    private var _binding: ColorPickerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ColorPickerBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireActivity()).setView(binding.root).create()
    }

    private lateinit var picker: ColorPicker
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        picker = binding.colorPicker
        picker.defaultColor = defaultColor
        binding.colorDone.setOnClickListener {
            picked(picker.getActiveColor())
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}