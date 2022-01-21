package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.AbstractRepository
import com.qoiu.dailytaskmotivator.data.RealmDataSource
import com.qoiu.dailytaskmotivator.data.SharedData
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskRepository(
    taskDataSource: RealmDataSource<TaskDb>,
    domainMapper: Mapper.Data<TaskDb, Task>,
    dbMapper: Mapper.Data<Task,TaskDb>,
    private val sharedData: SharedData
) : AbstractRepository<TaskDb, Task>(taskDataSource, domainMapper, dbMapper), Save<Int>,
    Read<Int> {

    override fun save(data: Int) {
        sharedData.save(data)
    }

    override fun read(): Int =
        sharedData.read()
}
