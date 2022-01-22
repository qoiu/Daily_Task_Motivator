package com.qoiu.dailytaskmotivator.data.category

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class CategoryToCategoryDbMapper : Mapper.Data<Category,CategoryDb>{
    override fun map(data: Category): CategoryDb = CategoryDb(
        data.title,
        data.expand,
        data.color
    )
}