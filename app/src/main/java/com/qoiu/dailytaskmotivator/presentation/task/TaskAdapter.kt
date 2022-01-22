package com.qoiu.dailytaskmotivator.presentation.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.TaskItemBinding
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class TaskAdapter(
    private var list: List<TaskWithCategories>,
    private val show: DialogShow,
    private val update: Update<TaskWithCategories>,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (TaskWithCategories) -> Unit,
    private val doneAction: (TaskWithCategories) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<TaskWithCategories>>() {

    fun update(data: List<TaskWithCategories>) {
        list = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is TaskWithCategories.Task -> 0
        else -> 1
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
            else ->CategoryViewHolder(
                TaskItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun onBindViewHolder(holder: BaseViewHolder<TaskWithCategories>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}