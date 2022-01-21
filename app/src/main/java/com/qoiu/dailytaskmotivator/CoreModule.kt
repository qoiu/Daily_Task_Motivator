package com.qoiu.dailytaskmotivator

import android.content.Context
import com.qoiu.dailytaskmotivator.data.SharedData
import com.qoiu.dailytaskmotivator.data.task.TaskDataSource
import com.qoiu.dailytaskmotivator.data.task.TaskRepository
import com.qoiu.dailytaskmotivator.domain.MainInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor

class CoreModule {

    lateinit var mainInteractor: MainInteractor
    lateinit var taskInteractor: TaskInteractor

    fun init(context: Context) {
        val resourceProvider = ResourceProvider.Shared(context)
        val stringProvider = ResourceProvider.String(context)
        val realmProvider = RealmProvider.Base(context)
        val realmSource = TaskDataSource(realmProvider)
        val sharedData = SharedData.Gold(resourceProvider.provideSharedPreference())
        val repository = TaskRepository(realmSource, sharedData)
        mainInteractor = MainInteractor.Base(repository)
        taskInteractor = TaskInteractor.Base(repository,stringProvider)
    }
}