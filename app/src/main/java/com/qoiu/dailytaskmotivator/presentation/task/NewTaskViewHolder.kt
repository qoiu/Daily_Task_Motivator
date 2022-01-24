package com.qoiu.dailytaskmotivator.presentation.task

import com.qoiu.dailytaskmotivator.databinding.TaskNewItemBinding
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class NewTaskViewHolder(
    private val view: TaskNewItemBinding,
    private val newTask: ()->Unit
) : BaseViewHolder<TaskWithCategories>(view.root) {
    override fun bind(data: TaskWithCategories) {
        view.newItemCardView.setOnClickListener {
            newTask()
        }
    }
}