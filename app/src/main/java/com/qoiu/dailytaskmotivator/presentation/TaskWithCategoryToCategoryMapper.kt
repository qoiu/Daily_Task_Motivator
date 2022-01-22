package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class TaskWithCategoryToCategoryMapper: Mapper.Data<TaskWithCategories,Category> {
    override fun map(data: TaskWithCategories): Category  =
        when(data){
            is TaskWithCategories.Category -> Category(
                data.title,
                data.expand,
                data.color
            )
            else -> {throw IllegalStateException("Can't map class ${data.javaClass.name}")}
        }
}