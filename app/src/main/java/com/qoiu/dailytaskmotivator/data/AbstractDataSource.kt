package com.qoiu.dailytaskmotivator.data

import com.qoiu.dailytaskmotivator.RealmProvider
import com.qoiu.dailytaskmotivator.UpdatableRealm
import io.realm.RealmAny

abstract class AbstractDataSource<T : UpdatableRealm<T>>(private val realmProvider: RealmProvider) :
    RealmDataSource<T> {

    abstract fun compareTo(data: T): RealmAny
    abstract val primaryKey: String
    abstract fun realmClass(): Class<T>

    override fun save(data: T) {
        realmProvider.provide().executeTransaction { realm ->
            realm.insertOrUpdate(data)
        }
    }

    override fun read(): List<T> {
        var list: List<T> = emptyList()
        realmProvider.provide().executeTransaction { realm ->
            val data = realm.where(realmClass())
                .findAll() ?: emptyList<T>()
            list = realm.copyFromRealm(data)
        }
        return list
    }

    override fun remove(data: T) {
        realmProvider.provide().executeTransaction { realm ->
            val obj = realm.where(realmClass()).equalTo(primaryKey, compareTo(data))
                .findAll()
            obj.deleteAllFromRealm()
        }
    }
}