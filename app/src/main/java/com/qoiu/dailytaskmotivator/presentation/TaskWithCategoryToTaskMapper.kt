package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskWithCategoryToTaskMapper: Mapper.Data<TaskWithCategories,Task> {
    override fun map(data: TaskWithCategories): Task  =
        when(data){
            is TaskWithCategories.Task -> Task(
                data.title,
                data.body,
                data.reward,
                data.expired,
                data.deadline,
                data.progressMax,
                data.currentProgress,
                data.dailyTask,
                data.reusable,
                data.category
            )
            is TaskWithCategories.Category->Task(
                title = data.title,
                category = data.title
            )
            else -> {throw IllegalStateException("Can't map class ${data.javaClass.name}")}
        }
}