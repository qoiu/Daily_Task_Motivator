package com.qoiu.dailytaskmotivator.presentation

import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.qoiu.dailytaskmotivator.databinding.ProgressModifierBinding
import kotlin.math.max
import kotlin.math.min

class ProgressModifierDialog(private val task: Structure.Task, private val update: (task: Structure.Task) -> Unit) :
    BaseDialogFragment<ProgressModifierBinding>() {

    override fun initBinding(): ProgressModifierBinding = ProgressModifierBinding.inflate(
        LayoutInflater.from(context))

    private lateinit var titleView: TextView
    private lateinit var editView: EditText
    private lateinit var seekView: SeekBar
    private lateinit var doneView: Button
    private var progress: Int = 0

    override fun init(binding: ProgressModifierBinding) {
        progress = task.currentProgress
        titleView = binding.progressText
        editView = binding.progressEdit
        seekView = binding.progressSeekbar
        doneView = binding.progressDone
        doneView.setOnClickListener {
            update(task.update(currentProgress = progress))
            dismiss()
        }
        updateTitle()
        editView.setText("$progress")
        seekView.max = task.progressMax
        seekView.progress = progress
        seekView.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editView.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        editView.doOnTextChanged { _, _, _, _ ->
            try {
                var int = editView.text.toString().toInt()
                int = max(0, min(task.progressMax, int))
                progress = int
            } catch (e: Exception) {
                editView.setText("$progress")
            }
            seekView.progress = progress
            updateTitle()
        }
    }

    private fun updateTitle() {
        val text = "${task.title} (${progress}/${task.progressMax})"
        titleView.text = text
    }
}