package com.qoiu.dailytaskmotivator.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.Observe

abstract class BaseViewModel<T>(
    protected val communication: Communication<T>
) : ViewModel(), Observe<T> {

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        communication.observe(owner, observer)
    }
}