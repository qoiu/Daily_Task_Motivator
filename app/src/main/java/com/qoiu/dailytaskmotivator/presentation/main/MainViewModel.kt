package com.qoiu.dailytaskmotivator.presentation.main

import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.data.task.TaskDb
import com.qoiu.dailytaskmotivator.domain.MainInteractor
import com.qoiu.dailytaskmotivator.presentation.BaseViewModel

class MainViewModel(private val interactor: MainInteractor, communication: Communication<List<TaskDb>>)
    : BaseViewModel<List<TaskDb>>(
    communication
), Save.Gold, Read.Gold {

    override fun save(data: Int) {
        interactor.save(data)
    }

    override fun read(): Int = interactor.read()
}