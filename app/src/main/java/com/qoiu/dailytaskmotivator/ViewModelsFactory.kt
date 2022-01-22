package com.qoiu.dailytaskmotivator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.presentation.main.MainViewModel
import com.qoiu.dailytaskmotivator.presentation.main.MainViewModelFactory
import com.qoiu.dailytaskmotivator.presentation.shop.ShopModel
import com.qoiu.dailytaskmotivator.presentation.shop.ShopModelFactory
import com.qoiu.dailytaskmotivator.presentation.task.TaskModel
import com.qoiu.dailytaskmotivator.presentation.task.TaskModelFactory

class ViewModelsFactory(coreModule: CoreModule) {
    private val map = HashMap<Class<out ViewModel>, ViewModelProvider.Factory>().apply {
        put(MainViewModel::class.java, MainViewModelFactory(coreModule.sharedProvider))
        put(TaskModel::class.java, TaskModelFactory(coreModule.taskInteractor,coreModule.categoryInteractor))
        put(ShopModel::class.java, ShopModelFactory())
    }

    fun get(modelClass: Class<out ViewModel>) =
        map[modelClass] ?: throw IllegalStateException("unknown viewModel $modelClass")
}