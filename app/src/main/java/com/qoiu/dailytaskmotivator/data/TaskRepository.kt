package com.qoiu.dailytaskmotivator.data

import android.util.Log
import com.qoiu.dailytaskmotivator.*
import com.qoiu.dailytaskmotivator.domain.Task

class TaskRepository(private val taskDataSource: RealmDataSource<TaskDb>, private val sharedData: SharedData) : Repository<TaskDb>, Save<Int>,
    Read<Int>, Update<TaskDb> {

    override suspend fun fetchData(): List<TaskDb> =
        try {
            taskDataSource.read()
        } catch (e: Exception) {
            emptyList()
        }

    override suspend fun save(data: TaskDb) {
        try{
            taskDataSource.save(data)
        }catch (e: Exception) {
            Log.e("TaskRepository",e.stackTraceToString())
        }
    }

    override suspend fun remove(task: TaskDb) {
        taskDataSource.remove(task)
    }

    override fun save(data: Int) {
        sharedData.save(data)
    }

    override fun read(): Int =
        sharedData.read()

    override fun update(data: TaskDb) {
        taskDataSource.update(data)
    }
}
