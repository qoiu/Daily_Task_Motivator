package com.qoiu.dailytaskmotivator.domain

import java.util.*

data class Task(
    val title: String,
    val value: Int,
    val deadline: Date
)