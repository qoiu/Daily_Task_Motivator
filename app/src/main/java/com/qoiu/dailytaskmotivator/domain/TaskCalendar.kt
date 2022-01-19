package com.qoiu.dailytaskmotivator.domain

import java.text.SimpleDateFormat
import java.util.*

class TaskCalendar(){
    fun tillTomorrow(): Date? {
        val now = dateFormatDateOnly.format(Date().time + DAY)
        return dateFormatDateOnly.parse(now)
    }

    fun now(): Date = dateFormatFull.parse(dateFormatFull.format(Date()))?:Date()

    fun today(): Date = dateFormatDateOnly.parse(dateFormatDateOnly.format(Date()))?:Date()
    fun formatDate(date: Long): String = dateFormatDateOnly.format(Date(date))
    fun getDate(year: Int, month: Int, day: Int): Long{
        calendar.set(year,month,day)
        return calendar.time.time
    }
    fun formatFromString(date: String): Long =
        dateFormatDateOnly.parse(date)?.time?:throw IllegalStateException("Wrong date format")

    fun daysAfterDeadline(deadline: Long) : Int {
        val time =today().time-deadline
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