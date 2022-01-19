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
import com.qoiu.dailytaskmotivator.data.TaskDb
import kotlin.math.max
import kotlin.math.min

class ProgressModifierDialog(private val task: TaskDb, private val update: () -> Unit) :
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView = view.findViewById(R.id.progress_text)
        editView = view.findViewById(R.id.progress_edit)
        seekView = view.findViewById(R.id.progress_seekbar)
        doneView = view.findViewById(R.id.progress_done)
        doneView.setOnClickListener {
            update()
            dismiss()
        }
        updateTitle()
        editView.setText("${task.currentProgress}")
        seekView.max = task.progressMax
        seekView.progress = task.currentProgress
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
                task.currentProgress = int
            } catch (e: Exception) {
                editView.setText("${task.currentProgress}")
            }
            seekView.progress = task.currentProgress
            updateTitle()
        }
    }

    private fun updateTitle() {
        val text = "${task.title} (${task.currentProgress}/${task.progressMax})"
        titleView.text = text
    }
}