package com.qoiu.dailytaskmotivator.presentation

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskModel(private val interactor: TaskInteractor) :
    BaseViewModel<List<TaskDb>>(TaskCommunication()) {


    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            communication.provide(interactor.loadTask())
        }
    }

    fun deleteTask(task: TaskDb) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.removeTask(task)
        }.invokeOnCompletion { updateData() }
    }

    fun update(task: TaskDb) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.update(task)
        }.invokeOnCompletion { updateData() }
    }
}