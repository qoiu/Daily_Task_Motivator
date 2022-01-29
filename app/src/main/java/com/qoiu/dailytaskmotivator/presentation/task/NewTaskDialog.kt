package com.qoiu.dailytaskmotivator.presentation.task

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.Structure
import kotlin.math.min

class NewTaskDialog(
    private val action: (task: Structure.Task) -> Unit,
    private val toast: (error: String) -> Unit,
    private val stringProvider: ResourceProvider.StringProvider,
    private val categoriesAdapter: ArrayAdapter<String>,
    private val taskType: Structure.Task = Structure.Task("")
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_new, container, false)
    }

    private lateinit var editableView: EditText
    private lateinit var calendar: CalendarView
    private lateinit var datesView: LinearLayout
    private lateinit var calendarView: LinearLayout
    private lateinit var dailyTaskView: CheckBox
    private lateinit var reusableTaskView: CheckBox
    private lateinit var titleView: EditText
    private lateinit var descriptionView: EditText
    private lateinit var rewardView: EditText
    private lateinit var progressView: EditText
    private lateinit var deadlineView: EditText
    private lateinit var expireView: EditText
    private lateinit var categoryView: AutoCompleteTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
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
        calendar = view.findViewById(R.id.calendarView)
        calendarView = view.findViewById(R.id.calendarLayout)
        categoryView = view.findViewById(R.id.edit_category)
        deadlineView.inputType = 0
        expireView.inputType = 0
        setActions(view)
        categoryView.setOnClickListener {
            categoryView.setText(categoryView.text.toString())
        }
        categoryView.setAdapter(categoriesAdapter)
        if (taskType.title.isNotEmpty())
            fillView(taskType)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(true)
        } else {
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    private fun setActions(view: View) {
        dailyTaskView.setOnCheckedChangeListener { _, _ ->
            datesVisible()
        }
        reusableTaskView.setOnCheckedChangeListener { _, _ ->
            datesVisible()
        }
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
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
        view.findViewById<Button>(R.id.edit_calendar_clear_btn).setOnClickListener {
            editableView.setText("")
        }
    }

    private fun fillView(task: Structure.Task) {
        titleView.setText(task.title)
        categoryView.setText(task.category)
        descriptionView.setText(task.body)
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

    private fun getTask(): Structure.Task {
        val reward: Int
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
        val expired = if (
            expireView.text.toString() == "" ||
            dailyTaskView.isChecked ||
            reusableTaskView.isChecked
        ) {
            0L
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
        return Structure.Task(
            titleView.text.toString(),
            descriptionView.text.toString(),
            reward,
            expired,
            deadline,
            progress,
            min(taskType.currentProgress, progress),
            dailyTaskView.isChecked,
            reusableTaskView.isChecked,
            categoryView.text.toString(),
            taskType.color
        )
    }

    private fun update(view: EditText, focused: Boolean) {
        if (view.inputType == EditText.AUTOFILL_TYPE_NONE && focused) {
            (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
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