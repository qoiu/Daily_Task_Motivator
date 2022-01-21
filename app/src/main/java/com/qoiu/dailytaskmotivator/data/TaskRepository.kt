package com.qoiu.dailytaskmotivator.data

import android.util.Log
import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save

class TaskRepository(private val taskDataSource: RealmDataSource<TaskDb>, private val sharedData: SharedData) : Repository<TaskDb>, Save<Int>,
    Read<Int> {

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
}
