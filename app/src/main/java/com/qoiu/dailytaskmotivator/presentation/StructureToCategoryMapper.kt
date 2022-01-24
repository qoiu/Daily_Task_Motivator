package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Category

class StructureToCategoryMapper: Mapper.Data<Structure,Category> {
    override fun map(data: Structure): Category  =
        when(data){
            is Structure.Category -> Category(
                data.title,
                data.expand,
                data.color
            )
            else -> {throw IllegalStateException("Can't map class ${data.javaClass.name}")}
        }
}