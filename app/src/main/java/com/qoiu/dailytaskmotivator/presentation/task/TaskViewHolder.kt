package com.qoiu.dailytaskmotivator.presentation.task

import android.graphics.Color
import android.view.View
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.TaskItemBinding
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.ProgressModifierDialog
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class TaskViewHolder(
    private val view: TaskItemBinding,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (TaskWithCategories) -> Unit,
    private val doneAction: (TaskWithCategories) -> Unit,
    private val show: DialogShow,
    private val update: Update<TaskWithCategories>
) : BaseViewHolder<TaskWithCategories>(view.root) {

    override fun bind(data: TaskWithCategories) {
        val task = data as TaskWithCategories.Task
        itemView.setOnClickListener {
            dialog(task)
        }
        if (task.color.isNotEmpty())
        view.taskBackground.setCardBackgroundColor(
            Color.parseColor(task.color)
        )
        view.taskTitle.text = task.title
        view.taskBody.apply {
            if (task.body != "") {
                text = task.body
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
        view.taskReward.apply {
            if (task.reward == 0) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                val str = if (task.reward < 0) {
                    setTextColor(Color.RED)
                    "${stringProvider.string(R.string.reward)}: ${task.reward * -1}"
                } else {
                    setTextColor(Color.GREEN)
                    "${stringProvider.string(R.string.cost)}: ${task.reward}"
                }
                text = str
            }
        }
        view.taskExpired.apply {
            if (task.expired == 0L || task.dailyTask || task.reusable) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                val str = "${stringProvider.string(R.string.expired)}: " +
                        TaskCalendar().formatDate(task.expired)
                text = str
            }
        }
        view.taskDeadline.apply {
            if (task.deadline == 0L || task.dailyTask || task.reusable) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                val str = "${stringProvider.string(R.string.deadline)}: " +
                        TaskCalendar().formatDate(task.deadline)
                text = str
            }
        }
        view.taskDone.setOnClickListener {
            if (task.progressMax == 0) {
                doneAction(task)
            } else {
                show.show(ProgressModifierDialog(task) {
                    if (task.progressMax == task.currentProgress) {
                        doneAction(task)
                    } else {
//                        notifyItemChanged(list.indexOf(task))
                        update.update(task)
                    }
                }.apply {
                    isCancelable = false
                })
            }
        }
        view.taskProgressBar.apply {
            if (task.progressMax > 0) {
                max = task.progressMax
                progress = task.currentProgress
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
        view.taskProgress.apply {
            if (task.progressMax > 0) {
                val progressText =
                    "${stringProvider.string(R.string.progress)}: ${task.currentProgress}/${task.progressMax}"
                visibility = View.VISIBLE
                text = progressText
            } else {
                visibility = View.GONE
            }
        }
        view.taskPalette.setOnClickListener {
            show.show(ColorPickerDialog {
                task.color = it
                update.update(task)
            })
        }
    }
}