package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class StructureToCategoryMapper : Mapper.Data<Structure.Category, Category> {
    override fun map(data: Structure.Category): Category = Category(
        data.title,
        data.expand,
        data.color
    )
}