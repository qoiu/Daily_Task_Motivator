package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskToStructureMapper(private val stringProvider: ResourceProvider.StringProvider): Mapper.Data<Task,Structure.Task> {
    override fun map(data: Task): Structure.Task {
        val color = if(data.color=="")
            "#ffffffff"
        else
            data.color
        return Structure.Task(
            data.title,
            data.body,
            data.reward,
            data.expired,
            data.deadline,
            data.progressMax,
            data.currentProgress,
            data.dailyTask,
            data.reusable,
            if(data.category!="")data.category else stringProvider.string(R.string.others),
            color
        )
    }
}