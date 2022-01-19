package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import java.lang.Exception
import java.lang.IllegalStateException

class NewTaskDialog(
    private val action: (task: TaskDb) -> Unit,
    private val toast: (error: String) -> Unit
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_new, container, false)
    }

    private lateinit var editableView: EditText
    private lateinit var calendarView: CalendarView
    private lateinit var datesView: LinearLayout
    private lateinit var dailyTaskView: CheckBox
    private lateinit var titleView: EditText
    private lateinit var descriptionView: EditText
    private lateinit var rewardView: EditText
    private lateinit var progressView: EditText
    private lateinit var deadlineView: EditText
    private lateinit var expireView: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView = view.findViewById(R.id.edit_title)
        datesView = view.findViewById(R.id.edit_dates)
        descriptionView = view.findViewById(R.id.edit_body)
        rewardView = view.findViewById(R.id.edit_reward)
        progressView = view.findViewById(R.id.edit_progress)
        deadlineView = view.findViewById(R.id.edit_deadline)
        expireView = view.findViewById(R.id.edit_expire)
        dailyTaskView = view.findViewById(R.id.edit_daily)
        calendarView = view.findViewById(R.id.calendarView)

        dailyTaskView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                datesView.visibility = View.GONE
            } else {
                datesView.visibility = View.VISIBLE
            }
        }
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            editableView.setText(
                TaskCalendar().formatDate(
                    TaskCalendar().getDate(year, month, dayOfMonth)
                )
            )
        }
        calendarView.visibility = View.GONE
        deadlineView.setOnFocusChangeListener { _, hasFocus ->
            update(deadlineView, hasFocus)
        }
        expireView.setOnFocusChangeListener { _, hasFocus ->
            update(expireView, hasFocus)
        }
        view.findViewById<Button>(R.id.edit_btn).setOnClickListener {
            try {
                val task = getTask()
                action(task)
                this.dismiss()
            } catch (e: Exception) {
                toast(e.localizedMessage ?: "strange error")
            }
        }
        view.findViewById<Button>(R.id.edit_cancel).setOnClickListener {
            this.dismiss()
        }
    }

    private fun getTask(): TaskDb {
        val reward: Int
        var expired: Long
        if (rewardView.text.toString() == "")
            throw IllegalStateException("Reward can't be 0")
        try {
            reward = rewardView.text.toString().toInt()
        } catch (e: Exception) {
            throw IllegalStateException("Incorrect reward")
        }
        val progress = if (progressView.text.toString() == "") {
            0
        } else {
            try {
                progressView.text.toString().toInt()
            } catch (e: Exception) {
                throw IllegalStateException("Incorrect progress value")
            }
        }
        expired = if (expireView.text.toString() == "") {
            0
        } else {
            try {
                TaskCalendar().formatFromString(expireView.text.toString())
            } catch (e: Exception) {
                throw IllegalStateException("Incorrect expired date")
            }
        }
        val deadline = if (deadlineView.text.toString() == "") {
            0
        } else {
            try {
                TaskCalendar().formatFromString(deadlineView.text.toString())
            } catch (e: Exception) {
                throw IllegalStateException("Incorrect deadline date")
            }
        }
        if (dailyTaskView.isChecked) expired = TaskCalendar().tillTomorrow()?.time ?: 0

        return TaskDb(
            titleView.text.toString(),
            descriptionView.text.toString(),
            reward,
            expired,
            deadline,
            progress,
            0,
            dailyTaskView.isChecked
        )
    }

    private fun update(view: EditText, focused: Boolean) {
        if (view.inputType == 20 && focused) {
            editableView = view
            calendarView.visibility = View.VISIBLE
        } else {
            calendarView.visibility = View.GONE
        }
    }
}