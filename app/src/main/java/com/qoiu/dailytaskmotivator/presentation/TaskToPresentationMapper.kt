package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskToPresentationMapper: Mapper.Data<Task,TaskWithCategories.Task> {
    override fun map(data: Task): TaskWithCategories.Task {
        return TaskWithCategories.Task(
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
    }
}