package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskDbToTaskMapper: Mapper.Data<TaskDb, Task> {
    override fun map(data: TaskDb): Task =
        Task(
            data.title,
            data.body,
            data.reward,
            data.expiredAt,
            data.deadline,
            data.progressMax,
            data.currentProgress,
            data.dailyTask,
            data.reusable,
            data.category
        )
}