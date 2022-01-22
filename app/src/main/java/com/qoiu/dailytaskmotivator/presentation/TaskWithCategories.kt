package com.qoiu.dailytaskmotivator.presentation

sealed class TaskWithCategories {

    class Task(
        val title: String,
        val body: String = "",
        var reward: Int = 0,
        var expired: Long = 0L,
        val deadline: Long = 0L,
        val progressMax: Int = 0,
        var currentProgress: Int = 0,
        val dailyTask: Boolean = false,
        val reusable: Boolean = false,
        var category: String="",
        val color: String=""
        // TODO: 22.01.2022   val visibility: HashMap<String,>
    ) : TaskWithCategories() {

    }

    class Category(
        val title: String,
        val color: String,
        val expand: Boolean
    ): TaskWithCategories()

    class Empty(): TaskWithCategories()
}