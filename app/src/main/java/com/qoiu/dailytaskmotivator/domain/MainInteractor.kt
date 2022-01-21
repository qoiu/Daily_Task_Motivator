package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.task.TaskDb
import com.qoiu.dailytaskmotivator.data.task.TaskRepository

interface MainInteractor : Save<Int>, Read<Int> {
    suspend fun loadTask(): List<TaskDb>

    class Base(private val repository: TaskRepository) : MainInteractor {
        override suspend fun loadTask(): List<TaskDb> =
            repository.fetchData()


        override fun save(data: Int) {
            repository.save(read()+data)
        }

        override fun read(): Int =
            repository.read()
    }
}