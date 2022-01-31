package com.qoiu.dailytaskmotivator.presentation.task

import com.qoiu.dailytaskmotivator.databinding.TaskNewItemBinding
import com.qoiu.dailytaskmotivator.presentation.BaseViewHolder
import com.qoiu.dailytaskmotivator.presentation.Structure

class NewTaskViewHolder(
    private val view: TaskNewItemBinding,
    private val newTask: ()->Unit
) : BaseViewHolder<Structure>(view) {
    override fun bind(data: Structure) {
        view.newItemCardView.setOnClickListener {
            newTask()
        }
    }
}