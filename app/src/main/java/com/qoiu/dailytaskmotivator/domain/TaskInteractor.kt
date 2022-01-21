package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.data.task.TaskRepository
import com.qoiu.dailytaskmotivator.domain.entities.DefaultTasks
import com.qoiu.dailytaskmotivator.domain.entities.Task

interface TaskInteractor {
    suspend fun loadTask(): List<Task>
    suspend fun removeTask(task: Task)
    suspend fun save(data: Task)

    class Base(private val repository: TaskRepository, private val stringProvider: ResourceProvider.StringProvider) : TaskInteractor {
        override suspend fun loadTask(): List<Task> {
            val tasks = repository.fetchData()
            if(tasks.isEmpty()){
                DefaultTasks(stringProvider).getDefault().forEach { save(it) }
                return DefaultTasks(stringProvider).getDefault()
            }
            val list = mutableListOf<Task>()
            tasks.forEach {
                if (it.deadline > 0)
                    TaskGoldCoficient.Base(it).modify()
                if (it.title == "" || it.reward == 0) {
                    removeTask(it)
                } else if (!(it.dailyTask && it.expired <= TaskCalendar().today().time)) {
                    list.add(it)
                }
            }
            return list
        }

        override suspend fun save(data: Task) {
            repository.save(data)
            loadTask()
        }

        override suspend fun removeTask(task: Task) {
            if (!task.dailyTask && !task.reusable) {
                repository.remove(task)
            } else {
                if(!task.reusable) {
                    task.expired = TaskCalendar().today().time
                    save(task)
                }
            }
        }
    }
}