package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.R
import kotlin.math.max
import kotlin.math.min

class ProgressModifierDialog(private val task: Structure.Task, private val update: (task: Structure.Task) -> Unit) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_modifier, container, false)
    }

    private lateinit var titleView: TextView
    private lateinit var editView: EditText
    private lateinit var seekView: SeekBar
    private lateinit var doneView: Button
    private var progress: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = task.currentProgress
        titleView = view.findViewById(R.id.progress_text)
        editView = view.findViewById(R.id.progress_edit)
        seekView = view.findViewById(R.id.progress_seekbar)
        doneView = view.findViewById(R.id.progress_done)
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
                int = max(0, int)
                int = min(task.progressMax, int)
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