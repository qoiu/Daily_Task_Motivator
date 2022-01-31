package com.qoiu.dailytaskmotivator.domain

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

private const val DAY = 86_400_000
class TaskCalendar(private val now: Date = Date(), private val format: String = "dd/MM/yyyy") {

    private fun formattedDate() = try {
        SimpleDateFormat(format, Locale.ENGLISH)
    }catch (e: IllegalArgumentException){
        throw IllegalStateException("Wrong date format +${e.message}")
    }

    fun tillTomorrow(): Date = dayFromToday(1)

    fun dayFromToday(afterDay: Int) : Date {
            val result = formattedDate().format(now.time + DAY*afterDay)
            return formattedDate().parse(result)!!
    }

    fun today(): Date = formattedDate().parse(formattedDate().format(now))!!

    fun formatDate(date: Long): String = formattedDate().format(Date(date))

    fun getDate(year: Int, month: Int, day: Int): Long{
         Calendar.getInstance().apply {
            clear()
            set(year,month,day,0,0)
            return time.time
        }
    }

    fun formatFromString(date: String): Long =
        formattedDate().parse(date)?.time!!

    fun daysAfterDeadline(deadline: Long) : Int {
        val result = ((today().time-deadline)/ DAY).toInt()
        if(result<0) return 0
        return result
    }
}