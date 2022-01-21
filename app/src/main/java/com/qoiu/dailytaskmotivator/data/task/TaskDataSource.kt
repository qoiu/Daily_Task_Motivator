package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.RealmProvider
import com.qoiu.dailytaskmotivator.data.AbstractDataSource
import io.realm.RealmAny

class TaskDataSource(
    private val realmProvider: RealmProvider, override val primaryKey: String="title"
) : AbstractDataSource<TaskDb>(realmProvider){

    override fun compareTo(data: TaskDb): RealmAny = RealmAny.valueOf(data.title)

    override fun realmClass(): Class<TaskDb> = TaskDb::class.java

}