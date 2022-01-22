package com.qoiu.dailytaskmotivator.presentation.task

import com.qoiu.dailytaskmotivator.databinding.TaskItemBinding
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class CategoryViewHolder(
    view: TaskItemBinding
) : BaseViewHolder<TaskWithCategories>(view.root) {
    override fun bind(data: TaskWithCategories) {
        val task = data as TaskWithCategories.Category
        TODO("Not yet implemented")
    }
}