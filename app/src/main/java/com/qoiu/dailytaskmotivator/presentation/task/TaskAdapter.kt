package com.qoiu.dailytaskmotivator.presentation.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.CategoryItemBinding
import com.qoiu.dailytaskmotivator.databinding.CategoryWithTaskItemBinding
import com.qoiu.dailytaskmotivator.databinding.TaskItemBinding
import com.qoiu.dailytaskmotivator.databinding.TaskNewItemBinding
import com.qoiu.dailytaskmotivator.presentation.BaseViewHolder
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.Structure

class TaskAdapter(
    private var list: List<Structure>,
    private val show: DialogShow,
    private val update: Update<Structure>,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (Structure) -> Unit,
    private val newTask: () -> Unit,
    private val doneAction: (Structure) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<Structure>>() {

    fun update(data: List<Structure>) {
        val util = DiffUtil.calculateDiff(DiffCallback(data))
        list = data
        util.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Structure.Task -> 0
        is Structure.Category -> 1
        is Structure.CategoryWithTask -> 2
        is Structure.NewTask -> 3
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Structure> =
        when (viewType) {
            0 -> TaskViewHolder(
                TaskItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                stringProvider,
                dialog,
                doneAction,
                show,
                update
            )
            1 -> CategoryViewHolder(
                CategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                update,
                show
            )
            2 -> CategoryWithTaskViewHolder(
                CategoryWithTaskItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), update, show, stringProvider, dialog, newTask, doneAction
            )
            else -> NewTaskViewHolder(
                TaskNewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), newTask
            )
        }

    override fun onBindViewHolder(holder: BaseViewHolder<Structure>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    private inner class DiffCallback(private val data: List<Structure>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = list.size

        override fun getNewListSize(): Int = data.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldTask = list[oldItemPosition]
            val newTask = data[newItemPosition]
            return oldTask.same(newTask)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldTask = list[oldItemPosition]
            val newTask = data[newItemPosition]
            return oldTask == newTask
        }
    }
}