package com.qoiu.dailytaskmotivator.data.category

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class CategoryDbToCategoryMapper : Mapper.Data<CategoryDb,Category>{
    override fun map(data: CategoryDb): Category = Category(
        data.title,
        data.expand,
        data.color
    )
}