package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.Task

class StructureToTaskMapper(private val stringProvider: ResourceProvider.StringProvider) :
    Mapper.Data<Structure.Task, Task> {
    override fun map(data: Structure.Task): Task = Task(
        data.title,
        data.body,
        data.reward,
        data.expired,
        data.deadline,
        data.progressMax,
        data.currentProgress,
        data.dailyTask,
        data.reusable,
        if (data.category != stringProvider.string(R.string.others)) data.category else "",
        data.color
    )
}