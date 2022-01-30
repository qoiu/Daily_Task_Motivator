package com.qoiu.dailytaskmotivator

import androidx.lifecycle.*
import io.realm.RealmModel

interface UpdatableRealm<T>: RealmModel,Update<T>

interface Builder<T>{
    fun built():T
}

interface ViewHolder<T>{
    fun bind(data: T)
}

interface Save<T> {
    fun save(data: T)
    interface Gold : Save<Int>
}

interface Read<T> {
    fun read(): T
    interface Gold : Read<Int>
}

interface Update<T> {
    fun update(data: T)
}

interface Remove<T> {
    fun remove(data: T)
}

interface Provide<T> {
    fun provide(data: T)
}

interface Observe<T> {
    fun observe(owner: LifecycleOwner, observer: Observer<T>)
}

interface ViewModelRequest {
    fun <T : ViewModel> getViewModel(model: Class<T>, owner: ViewModelStoreOwner): T
}

interface Execute<T>{
    fun execute(): T
}

interface Mapper{
    interface Data<I,O> :  Mapper{
        fun map(data: I): O
    }

    interface Object<I,O>: Mapper{
        fun map(mapper: Data<I,O>): O

    }
}

interface Communication<T> : Provide<T>, Observe<T> {

    open class Base<T : Any> : Communication<T> {
        private val liveData = MutableLiveData<T>()
        override fun provide(data: T) {
            liveData.postValue(data)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }
}