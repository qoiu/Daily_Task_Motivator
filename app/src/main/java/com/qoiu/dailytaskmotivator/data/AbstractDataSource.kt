package com.qoiu.dailytaskmotivator.data

import com.qoiu.dailytaskmotivator.RealmProvider
import com.qoiu.dailytaskmotivator.UpdatableRealm
import com.qoiu.dailytaskmotivator.data.category.CategoryDb
import io.realm.RealmAny

abstract class AbstractDataSource<T: UpdatableRealm<T>>(private val realmProvider: RealmProvider): RealmDataSource<T> {

    abstract fun compareTo(data: T): RealmAny
    abstract val primaryKey: String
    abstract fun realmClass(): Class<T>

    override fun save(data: T) {
        realmProvider.provide().executeTransaction { realm ->
            val obj = realm.where(realmClass()).equalTo(primaryKey, compareTo(data))
                .findFirst()
            obj?.update(data)?:realm.insert(data)
        }
    }

    override fun read(): List<T> {
        realmProvider.provide().use { realm ->
            val data = realm.where(realmClass())
                .findAll() ?: emptyList<T>()
            return realm.copyFromRealm(data)
        }
    }

    override fun remove(data: T) {
        realmProvider.provide().use { realm ->
            realm.executeTransaction {
                val obj = realm.where(CategoryDb::class.java).equalTo(primaryKey, compareTo(data))
                    .findAll()
                obj.deleteAllFromRealm()
            }
        }
    }
}