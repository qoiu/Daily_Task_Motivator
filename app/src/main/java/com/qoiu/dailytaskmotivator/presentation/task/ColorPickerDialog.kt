package com.qoiu.dailytaskmotivator.presentation.task

import android.view.LayoutInflater
import com.qoiu.dailytaskmotivator.databinding.ColorPickerBinding
import com.qoiu.dailytaskmotivator.presentation.BaseDialogFragment
import com.qoiu.dailytaskmotivator.presentation.ColorPicker

class ColorPickerDialog(
    private val picked: (int: String) -> Unit,
    private val defaultColor: String = "#ffffffff"
) : BaseDialogFragment<ColorPickerBinding>() {

    override fun initBinding(): ColorPickerBinding =
        ColorPickerBinding.inflate(LayoutInflater.from(context))

    private lateinit var picker: ColorPicker

    override fun init(binding: ColorPickerBinding) {
        picker = binding.colorPicker
        picker.defaultColor = defaultColor
        binding.colorDone.setOnClickListener {
            picked(picker.getActiveColor())
            dismiss()
        }
    }
}