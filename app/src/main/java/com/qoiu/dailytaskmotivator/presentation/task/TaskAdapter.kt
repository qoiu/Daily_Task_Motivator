package com.qoiu.dailytaskmotivator.presentation.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.CategoryItemBinding
import com.qoiu.dailytaskmotivator.databinding.TaskItemBinding
import com.qoiu.dailytaskmotivator.databinding.TaskNewItemBinding
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class TaskAdapter(
    private var list: List<TaskWithCategories>,
    private val show: DialogShow,
    private val update: Update<TaskWithCategories>,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (TaskWithCategories) -> Unit,
    private val newTask: ()->Unit,
    private val doneAction: (TaskWithCategories) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<TaskWithCategories>>() {

    fun update(data: List<TaskWithCategories>) {
        val util = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
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

        })
        list = data
        util.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is TaskWithCategories.Task -> 0
        is TaskWithCategories.Category -> 1
        else -> 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TaskWithCategories> =
        when (viewType) {
            0 -> TaskViewHolder(
                TaskItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false),
                stringProvider,
                dialog,
                doneAction,
                show,
                update
            )
            1 ->CategoryViewHolder(
                CategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false),
                update,
                show
            )
            else -> NewTaskViewHolder(
                TaskNewItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                ),newTask
            )
        }

    override fun onBindViewHolder(holder: BaseViewHolder<TaskWithCategories>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}