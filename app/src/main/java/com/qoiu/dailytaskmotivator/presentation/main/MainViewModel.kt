package com.qoiu.dailytaskmotivator.presentation.main

import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.presentation.BaseViewModel

class MainViewModel(
    communication: Communication<Int>,
    private val shared: ResourceProvider.Shared
)
    : BaseViewModel<Int>(
    communication
) {
    fun read(): Int = shared.provideSharedPreference().getInt(GOLD,0)

    fun save(gold: Int) {
        shared.provideSharedPreference().edit().putInt(GOLD,(read()+gold)).apply()
    }

    private companion object{
        const val GOLD="Gold"
    }
}