package com.qoiu.dailytaskmotivator.presentation.task

import android.graphics.Color
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.CategoryWithTaskItemBinding
import com.qoiu.dailytaskmotivator.presentation.BaseViewHolder
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.Structure
import com.qoiu.dailytaskmotivator.presentation.utils.ColorParse

class CategoryWithTaskViewHolder(
    private val view: CategoryWithTaskItemBinding,
    private val update: Update<Structure>,
    private val show: DialogShow,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (Structure) -> Unit,
    private val newTask: ()->Unit,
    private val doneAction: (Structure) -> Unit
): BaseViewHolder<Structure>(view) {
    override fun bind(data: Structure) {
        data as Structure.CategoryWithTask
        val category = data.category
        view.taskTitle.apply {
            text = category.title
            setTextColor(ColorParse(category.color).getFontColor())
        }
        view.categoryCard.setCardBackgroundColor(Color.parseColor(category.color))
        view.categoryCard.setOnClickListener {
            update.update(category.update(expand = !category.expand))
        }
        view.categoryPallete.setOnClickListener {
            show.show(ColorPickerDialog({
                update.update(category.update(color = it))
            },category.color))
        }
        view.categoryRecycler.adapter = TaskAdapter(
            data.tasks,show,update,stringProvider,dialog,newTask,doneAction
        )
    }
}