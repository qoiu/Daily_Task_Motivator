package com.qoiu.dailytaskmotivator

import android.content.Context
import com.qoiu.dailytaskmotivator.data.RealmDataSource
import com.qoiu.dailytaskmotivator.data.SharedData
import com.qoiu.dailytaskmotivator.data.TaskRepository
import com.qoiu.dailytaskmotivator.domain.MainInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor

class CoreModule {

    lateinit var mainInteractor: MainInteractor
    lateinit var taskInteractor: TaskInteractor

    fun init(context: Context) {
        val resourceProvider = ResourceProvider.Shared(context)
        val realmProvider = RealmProvider.Base(context)
        val realmSource = RealmDataSource.Base(realmProvider)
        val sharedData = SharedData.Gold(resourceProvider.provideSharedPreference())
        val repository = TaskRepository(realmSource, sharedData)
        mainInteractor = MainInteractor.Base(repository)
        taskInteractor = TaskInteractor.Base(repository)
    }
}