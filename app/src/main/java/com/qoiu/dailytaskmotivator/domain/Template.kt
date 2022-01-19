package com.qoiu.dailytaskmotivator.domain

data class Template(
    val title: String,
    val value: Int,
    val periodInHours: Int = 0
)