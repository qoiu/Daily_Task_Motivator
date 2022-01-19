package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.data.TaskRepository

interface MainInteractor : Save<Int>, Read<Int> {
    suspend fun loadTask(): List<TaskDb>
    suspend fun save(data: TaskDb)


    class Base(private val repository: TaskRepository) : MainInteractor {
        override suspend fun loadTask(): List<TaskDb> =
            repository.fetchData()

        override suspend fun save(data: TaskDb) {
            repository.save(data)
            loadTask()
        }

        override fun save(data: Int) {
            repository.save(read()+data)
        }

        override fun read(): Int =
            repository.read()
    }
}