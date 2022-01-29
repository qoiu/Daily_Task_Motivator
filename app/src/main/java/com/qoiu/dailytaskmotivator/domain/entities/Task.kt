package com.qoiu.dailytaskmotivator.domain.entities

data class Task(
    val title: String,
    val body: String = "",
    val reward: Int = 0,
    val expired: Long = 0L,
    val deadline: Long = 0L,
    val progressMax: Int = 0,
    val currentProgress: Int = 0,
    val dailyTask: Boolean = false,
    val reusable: Boolean = false,
    val category: String = "",
    val color: String = ""
) {

    fun update(
        body: String? = null,
        reward: Int? = null,
        expired: Long? = null,
        deadline: Long? = null,
        progressMax: Int? = null,
        currentProgress: Int? = null,
        dailyTask: Boolean? = null,
        reusable: Boolean? = null,
        category: String? = null,
        color: String? = null
    ) = Task(
        this.title,
        body ?: this.body,
        reward ?: this.reward,
        expired ?: this.expired,
        deadline ?: this.deadline,
        progressMax ?: this.progressMax,
        currentProgress ?: this.currentProgress,
        dailyTask ?: this.dailyTask,
        reusable ?: this.reusable,
        category ?: this.category,
        color ?: this.color
    )
}