package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.data.task.TaskDb

interface TaskGoldCoficient<T> {
    fun modify(): T

    class Base(private val data: TaskDb) : TaskGoldCoficient<TaskDb> {
        override fun modify(): TaskDb {
            val difference = TaskCalendar().daysAfterDeadline(data.deadline)
            return when (difference) {
                0 -> data
                1 -> multiply(data, 0.5)
                2 -> multiply(data, 0.4)
                3 -> multiply(data, 0.3)
                4 -> multiply(data, 0.2)
                5 -> multiply(data, 0.1)
                else -> multiply(data, 0.0)
            }
        }

        private fun multiply(data: TaskDb, modifier: Double): TaskDb {
            data.reward = (data.reward * modifier).toInt()
            return data
        }
    }
}