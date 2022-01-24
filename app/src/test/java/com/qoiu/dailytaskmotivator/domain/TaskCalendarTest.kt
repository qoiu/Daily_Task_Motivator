package com.qoiu.dailytaskmotivator.domain

import org.junit.Assert.*

import org.junit.Test
import java.util.*

class TaskCalendarTest {

    //day = 86_400_000
    private val today = 1642971600000L
    private val tomorrow = 1643058000000L
    private val todayPlusTime = today + 25_000_000

    @Test
    fun todaySimpleTest(){
        val actual = TaskCalendar(Date(todayPlusTime)).today().time
        assertEquals(today, actual)
    }


    @Test
    fun tillTomorrow() {
        val actual = TaskCalendar(Date(todayPlusTime)).tillTomorrow()?.time
        assertEquals(tomorrow, actual)
    }

    @Test
    fun now() {
        val actual = TaskCalendar(Date(todayPlusTime)).now().time
        assertEquals(todayPlusTime, actual)
    }

    @Test
    fun formatDate() {
        val actual = TaskCalendar().formatDate(todayPlusTime)
        val expected = "24/01/2022"
        assertEquals(expected, actual)
    }

    @Test
    fun getDate() {
        val base = TaskCalendar(Date(today)).getDate(2022,0,24)
        assertEquals(today,TaskCalendar(Date(base)).today().time)
    }

    @Test
    fun formatFromString() {
        val actual = TaskCalendar().formatFromString("24/01/2022")
        assertEquals(today,actual)
    }

    @Test
    fun daysAfterDeadline() {
        val actual = TaskCalendar(Date(tomorrow)).daysAfterDeadline(today)
        assertEquals(1,actual)
    }
}