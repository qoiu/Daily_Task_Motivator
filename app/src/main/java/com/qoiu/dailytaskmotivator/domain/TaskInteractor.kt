package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.DefaultTasks
import com.qoiu.dailytaskmotivator.domain.entities.Task

interface TaskInteractor {
    suspend fun loadTask(): List<Task>
    suspend fun removeTask(task: Task)
    suspend fun save(data: Task)

    class Base(
        private val repository: Repository<Task>,
        private val stringProvider: ResourceProvider.StringProvider
    ) : TaskInteractor {
        override suspend fun loadTask(): List<Task> {
            val tasks = repository.fetchData()
            if (tasks.isEmpty()) {
                DefaultTasks(stringProvider).getDefault().forEach { save(it) }
                return DefaultTasks(stringProvider).getDefault()
            }
            val list = mutableListOf<Task>()
            tasks.forEach {
                if (!it.dailyTask && !it.reusable && it.expired < TaskCalendar().today().time && it.expired!=0L)
                    removeTask(it)
                else if (it.title == "" || it.reward == 0)
                    removeTask(it)
                else if (it.deadline > 0)
                    TaskGoldCoficient.Base(it).modify()
                else if (!(it.dailyTask && it.expired > TaskCalendar().today().time))
                    list.add(it)
            }
            return list
        }

        override suspend fun save(data: Task) {
            repository.save(data)
        }

        override suspend fun removeTask(task: Task) {
            if (!task.dailyTask && !task.reusable) {
                repository.remove(task)
            } else {
                if (!task.reusable) {
                    if (task.progressMax > 0) task.currentProgress = 0
                    task.expired = TaskCalendar().tillTomorrow()?.time ?: 0
                    save(task)
                }else{
                    if (task.progressMax > 0) {
                        task.currentProgress = 0
                        save(task)
                    }
                }
            }
        }
    }
}