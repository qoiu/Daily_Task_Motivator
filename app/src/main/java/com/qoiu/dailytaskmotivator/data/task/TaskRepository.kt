package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.AbstractRepository
import com.qoiu.dailytaskmotivator.data.RealmDataSource
import com.qoiu.dailytaskmotivator.data.SharedData

class TaskRepository(taskDataSource: RealmDataSource<TaskDb>, private val sharedData: SharedData) : AbstractRepository<TaskDb>(taskDataSource), Save<Int>,
    Read<Int> {

    override fun save(data: Int) {
        sharedData.save(data)
    }

    override fun read(): Int =
        sharedData.read()
}
