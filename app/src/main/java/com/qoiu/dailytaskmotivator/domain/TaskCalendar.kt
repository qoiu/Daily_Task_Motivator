package com.qoiu.dailytaskmotivator.domain

import java.text.SimpleDateFormat
import java.util.*

class TaskCalendar(private val today: Date = Date()) {
    fun tillTomorrow(): Date? {
        val now = dateFormatDateOnly.format(today.time + DAY)
        return dateFormatDateOnly.parse(now)
    }

    fun now(): Date = dateFormatFull.parse(dateFormatFull.format(today))?:Date()

    fun today(): Date = dateFormatDateOnly.parse(dateFormatDateOnly.format(today))?:Date()
    fun formatDate(date: Long): String = dateFormatDateOnly.format(Date(date))
    fun getDate(year: Int, month: Int, day: Int): Long{
        calendar.clear()
        calendar.set(year,month,day,0,0)
        return calendar.time.time
    }
    fun formatFromString(date: String): Long =
        dateFormatDateOnly.parse(date)?.time?:throw IllegalStateException("Wrong date format")

    fun daysAfterDeadline(deadline: Long) : Int {
        val result = ((now().time-deadline)/ DAY).toInt()
        if(result<0) return 0
        return result
    }


    private companion object{
        val dateFormatFull = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val dateFormatDateOnly = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        const val DAY = 86_400_000
        val calendar = Calendar.getInstance()
    }
}