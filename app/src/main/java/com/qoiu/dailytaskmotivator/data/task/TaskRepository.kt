package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.data.AbstractRepository
import com.qoiu.dailytaskmotivator.data.RealmDataSource
import com.qoiu.dailytaskmotivator.domain.entities.Task

class TaskRepository(
    taskDataSource: RealmDataSource<TaskDb>,
    domainMapper: Mapper.Data<TaskDb, Task>,
    dbMapper: Mapper.Data<Task,TaskDb>
) : AbstractRepository<TaskDb, Task>(taskDataSource, domainMapper, dbMapper) {

}
