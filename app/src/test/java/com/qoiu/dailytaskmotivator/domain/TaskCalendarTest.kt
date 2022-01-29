package com.qoiu.dailytaskmotivator.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class TaskCalendarTest {

    private val day = 86_400_000
    private val today = 1642971600000L
    private val tomorrow = 1643058000000L
    private val todayPlusTime = today + 25_000_000

    @Test
    fun todaySimpleTest(){
        val actual = TaskCalendar(Date(todayPlusTime)).today().time
        assertEquals(today, actual)
    }

    @Test
    fun dayAfterToday(){
        val actual = TaskCalendar(Date(todayPlusTime)).dayFromToday(5)
        val expected = today+day*5
        assertEquals(expected, actual.time)
    }

    @Test
    fun tillTomorrow() {
        val actual = TaskCalendar(Date(todayPlusTime)).tillTomorrow().time
        assertEquals(tomorrow, actual)
    }


    @Test(expected = IllegalStateException::class)
    fun wrongFormat(){
        TaskCalendar(format = "poiur").dayFromToday(1)
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