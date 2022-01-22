package com.qoiu.dailytaskmotivator.domain.entities

import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.TaskCalendar

class DefaultTasks(private val stringProvider: ResourceProvider.StringProvider) {
    fun getDefault() = listOf(
        Task(
            title = stringProvider.string(R.string.new_task_title),
            reward = 20,
            expired = TaskCalendar().today().time
        )
    )
}