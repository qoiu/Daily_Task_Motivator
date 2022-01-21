package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.data.task.TaskDb

class DefaultTasks(private val stringProvider: ResourceProvider.StringProvider) {
    fun getDefault() = listOf(
        TaskDb(
            title = stringProvider.string(R.string.new_task_title),
            reward = 20,
            expiredAt = TaskCalendar().today().time
        )
    )
}