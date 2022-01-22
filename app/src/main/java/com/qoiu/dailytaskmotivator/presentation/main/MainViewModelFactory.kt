package com.qoiu.dailytaskmotivator.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.ResourceProvider

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val shared: ResourceProvider.Shared) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(Communication.Base(),shared) as T
}