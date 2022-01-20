package com.qoiu.dailytaskmotivator.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.domain.MainInteractor
import com.qoiu.dailytaskmotivator.presentation.task.TaskCommunication

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val interactor: MainInteractor) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(interactor, TaskCommunication()) as T
}