package com.qoiu.dailytaskmotivator.domain

interface Repository<T>{
    suspend fun fetchData(): List<T>
    suspend fun save(data: T)
    suspend fun remove(data: T)
}