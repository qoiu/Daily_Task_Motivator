package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.data.TaskDb

class TaskCommunication: Communication<List<TaskDb>> {
    private val liveData = MutableLiveData<List<TaskDb>>()
    override fun provide(data: List<TaskDb>) {
        liveData.postValue(data)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<TaskDb>>) {
        liveData.observe(owner, observer)
    }
}