package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Task

class StructureToTaskMapper: Mapper.Data<Structure,Task> {
    override fun map(data: Structure): Task  =
        when(data){
            is Structure.Task -> Task(
                data.title,
                data.body,
                data.reward,
                data.expired,
                data.deadline,
                data.progressMax,
                data.currentProgress,
                data.dailyTask,
                data.reusable,
                data.category,
                data.color
            )
            else -> {throw IllegalStateException("Can't map class ${data.javaClass.name}")}
        }
}