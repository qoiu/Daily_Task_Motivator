package com.qoiu.dailytaskmotivator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class App : Application() {

    private val coreModule = CoreModule()

    override fun onCreate() {
        super.onCreate()
        coreModule.init(this)
    }

    fun <T : ViewModel> viewModel(model: Class<T>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, ViewModelsFactory(coreModule).get(model)).get(model)
}