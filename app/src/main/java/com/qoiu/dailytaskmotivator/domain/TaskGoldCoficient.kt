package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.domain.entities.Task

interface TaskGoldCoficient<T> {
    fun modify()

    class Base(private val data: Task) : TaskGoldCoficient<Task> {
        override fun modify() {
            val difference = TaskCalendar().daysAfterDeadline(data.deadline)
            when (difference) {
                0 -> data
                1 -> multiply(data, 0.5)
                2 -> multiply(data, 0.4)
                3 -> multiply(data, 0.3)
                4 -> multiply(data, 0.2)
                5 -> multiply(data, 0.1)
                else -> multiply(data, 0.0)
            }
        }

        private fun multiply(data: Task, modifier: Double){
            data.reward = (data.reward * modifier).toInt()
        }
    }
}