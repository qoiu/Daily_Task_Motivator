package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.data.TaskRepository

interface TaskInteractor {
    suspend fun loadTask(): List<TaskDb>
    suspend fun removeTask(task: TaskDb)
    suspend fun update(task: TaskDb)
    suspend fun save(data: TaskDb)

    class Base(private val repository: TaskRepository, private val stringProvider: ResourceProvider.StringProvider) : TaskInteractor {
        override suspend fun loadTask(): List<TaskDb> {
            val tasks = repository.fetchData()
            if(tasks.isEmpty())return DefaultTasks(stringProvider).getDefault()
            val list = mutableListOf<TaskDb>()
            tasks.forEach {
                if (it.deadline > 0)
                    TaskGoldCoficient.Base(it).modify()
                if (it.title == "" || it.reward == 0) {
                    removeTask(it)
                } else if (!(it.dailyTask && it.expiredAt <= TaskCalendar().today().time)) {
                    list.add(it)
                }
            }
            return list
        }

        override suspend fun save(data: TaskDb) {
            repository.save(data)
            loadTask()
        }

        override suspend fun removeTask(task: TaskDb) {
            if (!task.dailyTask && !task.reusable) {
                repository.remove(task)
            } else {
                if(!task.reusable) {
                    task.expiredAt = TaskCalendar().today().time
                    update(task)
                }
            }
        }


        override suspend fun update(task: TaskDb) {
            repository.update(task)
        }
    }
}