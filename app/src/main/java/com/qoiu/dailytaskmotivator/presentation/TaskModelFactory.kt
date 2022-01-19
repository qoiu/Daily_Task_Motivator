package com.qoiu.dailytaskmotivator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.domain.TaskInteractor

class TaskModelFactory(private val interactor: TaskInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TaskModel(interactor) as T
}