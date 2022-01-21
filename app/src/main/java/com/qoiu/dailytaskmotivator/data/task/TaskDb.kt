package com.qoiu.dailytaskmotivator.data.task

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaskDb (
    @PrimaryKey
    var title: String = "",
    var body: String = "",
    var reward: Int=0,
    var expiredAt: Long = 0L,
    var deadline: Long = 0L,
    var progressMax: Int = 0,
    var currentProgress: Int = 0,
    var dailyTask: Boolean = false,
    var reusable: Boolean = false,
    var category: String = ""
) : RealmObject(){

    fun update(new: TaskDb){
        body = new.body
        reward = new.reward
        expiredAt = new.expiredAt
        deadline = new.deadline
        progressMax = new.progressMax
        currentProgress = new.currentProgress
        dailyTask = new.dailyTask
    }
}