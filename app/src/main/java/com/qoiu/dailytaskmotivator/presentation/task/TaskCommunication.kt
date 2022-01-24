package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.presentation.Structure

class TaskCommunication: Communication<List<Structure>> {
    private val liveData = MutableLiveData<List<Structure>>()
    override fun provide(data: List<Structure>) {
        liveData.postValue(data)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<Structure>>) {
        liveData.observe(owner, observer)
    }
}