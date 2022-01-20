package com.qoiu.dailytaskmotivator.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShopModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ShopModel() as T
}