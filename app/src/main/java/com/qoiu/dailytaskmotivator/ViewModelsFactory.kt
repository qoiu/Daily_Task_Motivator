package com.qoiu.dailytaskmotivator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.presentation.MainViewModel
import com.qoiu.dailytaskmotivator.presentation.MainViewModelFactory
import com.qoiu.dailytaskmotivator.presentation.TaskModel
import com.qoiu.dailytaskmotivator.presentation.TaskModelFactory

class ViewModelsFactory(coreModule: CoreModule) {
    private val map = HashMap<Class<out ViewModel>, ViewModelProvider.Factory>().apply {
        put(MainViewModel::class.java, MainViewModelFactory(coreModule.mainInteractor))
        put(TaskModel::class.java, TaskModelFactory(coreModule.taskInteractor))
    }

    fun get(modelClass: Class<out ViewModel>) =
        map[modelClass] ?: throw IllegalStateException("unknown viewModel $modelClass")
}