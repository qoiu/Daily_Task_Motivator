package com.qoiu.dailytaskmotivator.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.qoiu.dailytaskmotivator.App

abstract class BaseActivity : AppCompatActivity() {

    fun <T : ViewModel> viewModel(model: Class<T>, owner: ViewModelStoreOwner) =
        (application as App).viewModel(model, owner)
}