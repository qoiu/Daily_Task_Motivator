package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class CategoryToPresentationMapper: Mapper.Data<Category,TaskWithCategories.Category> {
    override fun map(data: Category): TaskWithCategories.Category {
        return TaskWithCategories.Category(
            data.title,
             data.expand,
            data.color
        )
    }
}