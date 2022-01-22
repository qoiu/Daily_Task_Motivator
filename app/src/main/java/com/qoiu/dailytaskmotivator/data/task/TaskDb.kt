package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.UpdatableRealm
import com.qoiu.dailytaskmotivator.domain.entities.Task
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
) : RealmObject(), UpdatableRealm<TaskDb>{

    override fun update(data: TaskDb){
        body = data.body
        reward = data.reward
        expiredAt = data.expiredAt
        deadline = data.deadline
        progressMax = data.progressMax
        currentProgress = data.currentProgress
        dailyTask = data.dailyTask
    }
}