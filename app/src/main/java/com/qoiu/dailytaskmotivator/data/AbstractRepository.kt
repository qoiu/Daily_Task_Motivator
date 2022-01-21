package com.qoiu.dailytaskmotivator.data

import android.util.Log

abstract class AbstractRepository<T>(private val dataSource: RealmDataSource<T>):Repository<T> {
    override suspend fun fetchData(): List<T> =
        try {
            dataSource.read()
        } catch (e: Exception) {
            emptyList()
        }

    override suspend fun save(data: T) {
        try{
            dataSource.save(data)
        }catch (e: Exception) {
            Log.e("TaskRepository",e.stackTraceToString())
        }
    }

    override suspend fun remove(data: T) {
        dataSource.remove(data)
    }

}