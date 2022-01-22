package com.qoiu.dailytaskmotivator.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class NewTaskDialog(
    private val action: (task: TaskWithCategories.Task) -> Unit,
    private val toast: (error: String) -> Unit,
    private val stringProvider: ResourceProvider.StringProvider,
    private val taskType: TaskWithCategories = TaskWithCategories.Empty()
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
    private lateinit var reusableTaskView: CheckBox
    private lateinit var titleView: EditText
    private lateinit var descriptionView: EditText
    private lateinit var rewardView: EditText
    private lateinit var progressView: EditText
    private lateinit var deadlineView: EditText
    private lateinit var expireView: EditText
    private lateinit var categoryView: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun setActions(view: View) {
        dailyTaskView.setOnCheckedChangeListener { _, _ ->
            datesVisible()
        }
        reusableTaskView.setOnCheckedChangeListener { _, _ ->
            datesVisible()
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
                toast(e.localizedMessage ?: stringProvider.string(R.string.error_strange))
            }
        }
        view.findViewById<Button>(R.id.edit_cancel).setOnClickListener {
            this.dismiss()
        }
    }

    private fun init(view: View) {
        titleView = view.findViewById(R.id.edit_title)
        datesView = view.findViewById(R.id.edit_dates)
        descriptionView = view.findViewById(R.id.edit_body)
        rewardView = view.findViewById(R.id.edit_reward)
        progressView = view.findViewById(R.id.edit_progress)
        deadlineView = view.findViewById(R.id.edit_deadline)
        expireView = view.findViewById(R.id.edit_expire)
        dailyTaskView = view.findViewById(R.id.edit_daily)
        reusableTaskView = view.findViewById(R.id.edit_reusable)
        calendarView = view.findViewById(R.id.calendarView)
        categoryView = view.findViewById(R.id.edit_category)
        setActions(view)
        if(taskType is TaskWithCategories.Task)
        fillView(taskType)
    }

    private fun fillView(task: TaskWithCategories.Task) {
        titleView.setText(task.title)
        categoryView.setText(task.category)
        descriptionView.setText(task.body)
        if (task.reward > 0)
            rewardView.setText(task.reward.toString())
        if (task.progressMax > 0)
            progressView.setText(task.progressMax.toString())
        dailyTaskView.isChecked = task.dailyTask
        reusableTaskView.isChecked = task.reusable
        if (task.deadline > 0)
            deadlineView.setText(TaskCalendar().formatDate(task.deadline))
        if (task.expired > 0)
            expireView.setText(TaskCalendar().formatDate(task.expired))
    }

    private fun getTask(): TaskWithCategories.Task {
        val reward: Int
        var expired: Long
        if (rewardView.text.toString() == "")
            throw IllegalStateException(stringProvider.string(R.string.error_reward))
        try {
            reward = rewardView.text.toString().toInt()
        } catch (e: Exception) {
            throw IllegalStateException(stringProvider.string(R.string.error_reward_incorrect))
        }
        val progress = if (progressView.text.toString() == "") {
            0
        } else {
            try {
                progressView.text.toString().toInt()
            } catch (e: Exception) {
                throw IllegalStateException(stringProvider.string(R.string.error_progress))
            }
        }
        expired = if (expireView.text.toString() == "") {
            0
        } else {
            try {
                TaskCalendar().formatFromString(expireView.text.toString())
            } catch (e: Exception) {
                throw IllegalStateException(stringProvider.string(R.string.error_expired))
            }
        }
        val deadline = if (deadlineView.text.toString() == "") {
            0
        } else {
            try {
                TaskCalendar().formatFromString(deadlineView.text.toString())
            } catch (e: Exception) {
                throw IllegalStateException(stringProvider.string(R.string.error_deadline))
            }
        }
        if (dailyTaskView.isChecked) expired = TaskCalendar().tillTomorrow()?.time ?: 0

        return TaskWithCategories.Task(
            titleView.text.toString(),
            descriptionView.text.toString(),
            reward,
            expired,
            deadline,
            progress,
            0,
            dailyTaskView.isChecked,
            reusableTaskView.isChecked,
            categoryView.text.toString()
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

    private fun datesVisible() {
        if (dailyTaskView.isChecked || reusableTaskView.isChecked) {
            datesView.visibility = View.GONE
        } else {
            datesView.visibility = View.VISIBLE
        }
    }
}