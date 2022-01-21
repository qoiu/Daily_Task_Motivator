package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.task.TaskDb
import com.qoiu.dailytaskmotivator.data.task.TaskRepository
import com.qoiu.dailytaskmotivator.domain.entities.Task

interface MainInteractor : Save<Int>, Read<Int> {
    suspend fun loadTask(): List<Task>

    class Base(private val repository: TaskRepository) : MainInteractor {
        override suspend fun loadTask(): List<Task> =
            repository.fetchData()


        override fun save(data: Int) {
            repository.save(read()+data)
        }

        override fun read(): Int =
            repository.read()
    }
}