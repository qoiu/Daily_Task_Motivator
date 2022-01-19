package com.qoiu.dailytaskmotivator.data

interface Repository<T>{
    suspend fun fetchData(): List<T>
    suspend fun save(data: T)
    suspend fun remove(task: T)
}