package com.qoiu.dailytaskmotivator.presentation.main

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.domain.MainInteractor
import com.qoiu.dailytaskmotivator.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: MainInteractor, communication: Communication<List<TaskDb>>)
    : BaseViewModel<List<TaskDb>>(
    communication
), Save.Gold, Read.Gold {

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            communication.provide(interactor.loadTask())
        }
    }

    fun addTask(taskDb: TaskDb){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.save(taskDb)
        }.invokeOnCompletion {
            getData()
        }
    }

    override fun save(data: Int) {
        interactor.save(data)
    }

    override fun read(): Int = interactor.read()
}