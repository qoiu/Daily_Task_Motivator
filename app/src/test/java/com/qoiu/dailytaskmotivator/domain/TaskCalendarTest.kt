package com.qoiu.dailytaskmotivator.domain

import org.junit.Test
import java.util.*

class TaskCalendarTest{
    @Test
    fun tillTomorrow(){
        println("now ${TaskCalendar().now()}")
        println(TaskCalendar().tillTomorrow())
        println(TaskCalendar().tillTomorrow()?.time)
        println("long ${Long.MAX_VALUE}")
    }

    @Test
    fun formatDate(){
        println(Date().time)
        println(TaskCalendar().formatDate(Date().time))
    }
}