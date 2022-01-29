package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.domain.entities.Task
import org.junit.Assert.*

import org.junit.Test

class TaskGoldCoefficientTest {

    private val map = listOf(10,5,4,3,2,1,0)
    @Test
    fun modify() {
        for(i in 0..6){
            val expired = TaskCalendar().dayFromToday(-i).time
            val actual = TaskGoldCoefficient.Base(Task("task", reward = 10, deadline = expired)).modify()
            assertEquals(
                Task("task", reward = map[i], deadline = expired), actual)
        }
    }
}