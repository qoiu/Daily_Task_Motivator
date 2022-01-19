package com.qoiu.dailytaskmotivator.data

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
    var dailyTask: Boolean = false
) : RealmObject(){

    fun update(new: TaskDb){
        currentProgress = new.currentProgress
        expiredAt = new.expiredAt
    }
}