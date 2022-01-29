package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.domain.entities.Task

interface TaskGoldCoefficient<T> {
    fun modify(): T

    class Base(private val data: Task) : TaskGoldCoefficient<Task> {
        override fun modify(): Task {
            val daysAfterDeadline = TaskCalendar().daysAfterDeadline(data.deadline)
            if (daysAfterDeadline < 0) return data
            val modifier: Double = when (
                daysAfterDeadline) {
                0 -> 1.0
                1 -> 0.5
                2 -> 0.4
                3 -> 0.3
                4 -> 0.2
                5 -> 0.1
                else -> 0.0
            }
            return data.update(reward = (data.reward * modifier).toInt())
        }
    }
}