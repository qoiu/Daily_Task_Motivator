package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskModel(private val interactor: TaskInteractor) :
    BaseViewModel<List<TaskDb>>(TaskCommunication()) {

    fun addTask(taskDb: TaskDb){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.save(taskDb)
        }.invokeOnCompletion {
            updateData()
        }
    }

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