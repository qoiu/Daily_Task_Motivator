package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskToTaskDbMapper: Mapper.Data<Task,TaskDb> {
    override fun map(data: Task): TaskDb =
        TaskDb(
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
}