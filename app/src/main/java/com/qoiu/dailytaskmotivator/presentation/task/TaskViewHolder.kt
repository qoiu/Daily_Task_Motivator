package com.qoiu.dailytaskmotivator.presentation.task

import android.graphics.Color
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.TaskItemBinding
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.ProgressModifierDialog
import com.qoiu.dailytaskmotivator.presentation.Structure
import com.qoiu.dailytaskmotivator.presentation.Structure.Task.Companion.Attr.*
import com.qoiu.dailytaskmotivator.presentation.utils.ColorParse

class TaskViewHolder(
    private val view: TaskItemBinding,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (Structure) -> Unit,
    private val doneAction: (Structure) -> Unit,
    private val show: DialogShow,
    private val update: Update<Structure>
) : BaseViewHolder<Structure>(view.root) {

    override fun bind(data: Structure) {
        val task = data as Structure.Task
        val fontColor = ColorParse(data.color).getFontColor()
        itemView.setOnClickListener {
            dialog(task)
        }
        if (task.color.isNotEmpty())
            view.taskBackground.setCardBackgroundColor(
                Color.parseColor(task.color)
            )
        view.taskTitle.apply {
            text = task.title
            setTextColor(fontColor)
        }
        view.taskBody.apply {
            text = task.body
            setTextColor(fontColor)
            visibility = task.isVisible(BODY)
        }
        view.taskReward.apply {

            val str = if (task.reward < 0) {
                setTextColor(ColorParse(task.color).getFontColor("#FFFFAAAA","#FFBB5555"))
                "${stringProvider.string(R.string.price)}: ${task.reward * -1}"
            } else {
                setTextColor(ColorParse(task.color).getFontColor("#FFAAFFAA","#FF33AA33"))
                "${stringProvider.string(R.string.reward)}: ${task.reward}"
            }
            text = str
        }
        view.taskExpired.apply {
            visibility = task.isVisible(EXPIRED)
            setTextColor(fontColor)
            val str = "${stringProvider.string(R.string.expired)}: " +
                    TaskCalendar().formatDate(task.expired)
            text = str
        }
        view.taskDeadline.apply {
            visibility = task.isVisible(DEADLINE)
            setTextColor(fontColor)
            val str = "${stringProvider.string(R.string.deadline)}: " +
                    TaskCalendar().formatDate(task.deadline)
            text = str
        }
        view.taskDone.setOnClickListener {
            if (task.progressMax == 0) {
                doneAction(task)
            } else {
                show.show(ProgressModifierDialog(task) {
                    if (it.progressMax == it.currentProgress) {
                        doneAction(it)
                    } else {
                        update.update(it)
                    }
                }.apply {
                    isCancelable = false
                })
            }
        }
        view.taskProgressBar.apply {
            max = task.progressMax
            progress = task.currentProgress
            visibility = task.isVisible(PROGRESS)
        }
        view.taskProgress.apply {
            val progressText =
                "${stringProvider.string(R.string.progress)}: ${task.currentProgress}/${task.progressMax}"
            setTextColor(fontColor)
            text = progressText
            visibility = task.isVisible(PROGRESS)
        }
        view.taskPalette.setOnClickListener {
            show.show(ColorPickerDialog ({
                update.update(task.update(color = it))
            },data.color))
        }
    }
}