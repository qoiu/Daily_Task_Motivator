package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class TaskCommunication: Communication<List<TaskWithCategories>> {
    private val liveData = MutableLiveData<List<TaskWithCategories>>()
    override fun provide(data: List<TaskWithCategories>) {
        liveData.postValue(data)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<TaskWithCategories>>) {
        liveData.observe(owner, observer)
    }
}