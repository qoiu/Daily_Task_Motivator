package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class CategoryToStructureMapper: Mapper.Data<Category,Structure.Category> {
    override fun map(data: Category): Structure.Category {
        return Structure.Category(
            data.title,
             data.expand,
            data.color
        )
    }
}