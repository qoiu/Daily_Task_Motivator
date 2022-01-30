package com.qoiu.dailytaskmotivator.presentation.task

import android.graphics.Color
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.CategoryItemBinding
import com.qoiu.dailytaskmotivator.presentation.BaseViewHolder
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.Structure
import com.qoiu.dailytaskmotivator.presentation.utils.ColorParse

class CategoryViewHolder(
    private val view: CategoryItemBinding,
    private val update: Update<Structure>,
    private val show: DialogShow
) : BaseViewHolder<Structure>(view) {
    override fun bind(data: Structure) {
        val category = data as Structure.Category
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