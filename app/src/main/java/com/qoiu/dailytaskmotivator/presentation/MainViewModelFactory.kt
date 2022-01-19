package com.qoiu.dailytaskmotivator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.domain.MainInteractor

class MainViewModelFactory(private val interactor: MainInteractor) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(interactor, TaskCommunication()) as T
}