package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.DefaultTasks
import com.qoiu.dailytaskmotivator.domain.entities.Task

interface TaskInteractor {
    suspend fun loadTask(): List<Task>
    suspend fun removeTask(data: Task)
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
                if (!it.dailyTask && !it.reusable && it.expired < TaskCalendar().today().time && it.expired != 0L)
                    removeTask(it)
                else if (it.title == "" || it.reward == 0)
                    removeTask(it)
                else if (it.deadline > 0) {
                    TaskGoldCoefficient.Base(it).modify()
                    list.add(it)
                }
                else if (!(it.dailyTask && it.expired > TaskCalendar().today().time))
                    list.add(it)
            }
            return list
        }

        override suspend fun save(data: Task) {
            repository.save(data)
        }

        override suspend fun removeTask(data: Task) {
            if (!data.dailyTask && !data.reusable) {
                repository.remove(data)
            } else {
                if (!data.reusable) {
                    if (data.progressMax > 0)
                        save(
                            data.update(
                                currentProgress = 0,
                                expired = TaskCalendar().tillTomorrow().time
                            )
                        )
                } else {
                    if (data.progressMax > 0)
                        save(data.update(currentProgress = 0))
                }
            }
        }
    }
}