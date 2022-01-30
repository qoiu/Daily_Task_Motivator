package com.qoiu.dailytaskmotivator.presentation.task

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.databinding.TaskNewBinding
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.BaseDialogFragment
import com.qoiu.dailytaskmotivator.presentation.Structure
import com.qoiu.dailytaskmotivator.presentation.StructureTaskBuilder

class NewTaskDialog(
    private val action: (task: Structure.Task) -> Unit,
    private val toast: (error: String) -> Unit,
    private val stringProvider: ResourceProvider.StringProvider,
    private val categoriesAdapter: ArrayAdapter<String>,
    private val taskType: Structure.Task = Structure.Task("")
) : BaseDialogFragment<TaskNewBinding>() {

    override fun initBinding(): TaskNewBinding =
        TaskNewBinding.inflate(LayoutInflater.from(context))

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

    override fun init(binding: TaskNewBinding) {
        titleView = binding.editTitle
        datesView = binding.editDates
        descriptionView = binding.editBody
        rewardView = binding.editReward
        progressView = binding.editProgress
        deadlineView = binding.editDeadline
        expireView = binding.editExpire
        dailyTaskView = binding.editDaily
        reusableTaskView = binding.editReusable
        calendar = binding.calendarView
        calendarView = binding.calendarLayout
        categoryView = binding.editCategory
        deadlineView.inputType = 0
        expireView.inputType = 0
        setActions(binding)
        if (taskType.title.isNotEmpty())
            fillView(taskType)
        hideKeyboard()
    }

    @Suppress("deprecation")
    private fun hideKeyboard() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(true)
        } else {
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    private fun setActions(binding: TaskNewBinding) {
        categoryView.setOnClickListener { categoryView.setText(categoryView.text.toString()) }
        categoryView.setAdapter(categoriesAdapter)
        dailyTaskView.setOnCheckedChangeListener { _, _ -> datesVisible() }
        reusableTaskView.setOnCheckedChangeListener { _, _ -> datesVisible() }
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            editableView.setText(
                TaskCalendar().formatDate(
                    TaskCalendar().getDate(year, month, dayOfMonth)
                )
            )
        }
        calendarView.visibility = View.GONE
        deadlineView.setOnFocusChangeListener { _, hasFocus -> update(deadlineView, hasFocus) }
        expireView.setOnFocusChangeListener { _, hasFocus -> update(expireView, hasFocus) }
        binding.editBtn.setOnClickListener {
            try {
                val task = getTask()
                action(task)
                this.dismiss()
            } catch (e: Exception) {
                toast(e.localizedMessage ?: stringProvider.string(R.string.error_strange))
            }
        }
        binding.editCancel.setOnClickListener { this.dismiss() }
        binding.editCalendarClearBtn.setOnClickListener { editableView.setText("") }
    }

    private fun fillView(task: Structure.Task) {
        titleView.setText(task.title)
        categoryView.setText(task.category)
        descriptionView.setText(task.body)
        rewardView.setText(task.reward.toString())
        if (task.progressMax > 0) progressView.setText(task.progressMax.toString())
        dailyTaskView.isChecked = task.dailyTask
        reusableTaskView.isChecked = task.reusable
        if (task.deadline > 0) deadlineView.setText(TaskCalendar().formatDate(task.deadline))
        if (task.expired > 0) expireView.setText(TaskCalendar().formatDate(task.expired))
    }

    private fun getTask(): Structure.Task = StructureTaskBuilder(stringProvider)
        .title(titleView.text.toString())
        .description(descriptionView.text.toString())
        .reward(rewardView.text.toString())
        .expired(expireView.text.toString())
        .deadline(deadlineView.text.toString())
        .progress(progressView.text.toString())
        .currentProgress(taskType.currentProgress)
        .dailyTask(dailyTaskView.isChecked)
        .reusable(reusableTaskView.isChecked)
        .category(categoryView.text.toString())
        .color(taskType.color)
        .built()

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