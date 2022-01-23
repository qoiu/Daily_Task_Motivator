package com.qoiu.dailytaskmotivator.presentation.task

import android.graphics.Color
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.CategoryItemBinding
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories
import com.qoiu.dailytaskmotivator.presentation.utils.ColorParse

class CategoryViewHolder(
    private val view: CategoryItemBinding,
    private val update: Update<TaskWithCategories>,
    private val show: DialogShow
) : BaseViewHolder<TaskWithCategories>(view.root) {
    override fun bind(data: TaskWithCategories) {
        val category = data as TaskWithCategories.Category
        view.taskTitle.apply {
            text = category.title
            setTextColor(ColorParse(data.color).getFontColor())
        }
        view.categoryCard.setCardBackgroundColor(Color.parseColor(category.color))
        view.categoryCard.setOnClickListener {
            update.update(data.update(expand = !data.expand))
        }
        view.categoryPallete.setOnClickListener {
            show.show(ColorPickerDialog({
                update.update(data.update(color = it))
            },data.color))
        }
    }
}