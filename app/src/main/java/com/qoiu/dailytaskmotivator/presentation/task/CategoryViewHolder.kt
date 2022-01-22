package com.qoiu.dailytaskmotivator.presentation.task

import android.graphics.Color
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.CategoryItemBinding
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class CategoryViewHolder(
    private val view: CategoryItemBinding,
    private val update: Update<TaskWithCategories>,
    private val show: DialogShow
) : BaseViewHolder<TaskWithCategories>(view.root) {
    override fun bind(data: TaskWithCategories) {
        val task = data as TaskWithCategories.Category
        view.taskTitle.text = task.title
        view.categoryCard.setCardBackgroundColor(Color.parseColor(task.color))
        view.categoryCard.setOnClickListener {
            update.update(TaskWithCategories.Category(data.title, data.color, !data.expand))
        }
        view.categoryPallete.setOnClickListener {
            show.show(ColorPickerDialog {
                update.update(TaskWithCategories.Category(data.title, it, !data.expand))
            })
        }
    }
}