package com.qoiu.dailytaskmotivator.domain.entities

import android.graphics.Color

data class Task(
    val title: String,
    val body: String = "",
    var reward: Int = 0,
    var expired: Long = 0L,
    val deadline: Long = 0L,
    val progressMax: Int = 0,
    var currentProgress: Int = 0,
    val dailyTask: Boolean = false,
    val reusable: Boolean = false,
    val category: String = "",
    val color: String = ""
)