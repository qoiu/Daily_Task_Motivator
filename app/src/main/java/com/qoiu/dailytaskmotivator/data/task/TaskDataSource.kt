package com.qoiu.dailytaskmotivator.data.task

import com.qoiu.dailytaskmotivator.RealmProvider
import com.qoiu.dailytaskmotivator.data.RealmDataSource

class TaskDataSource(
    private val realmProvider: RealmProvider
) : RealmDataSource<TaskDb> {
    override fun read(): List<TaskDb> {
        realmProvider.provide().use { realm ->
            val task = realm.where(TaskDb::class.java)
                .findAll() ?: emptyList<TaskDb>()
            return realm.copyFromRealm(task)
        }
    }

    override fun save(data: TaskDb) {
        realmProvider.provide().executeTransaction { realm ->
            val task = realm.where(TaskDb::class.java).equalTo("title", data.title)
                .findFirst()
            task?.update(data)?:realm.insert(data)
        }
    }

    override fun remove(data: TaskDb) {
        realmProvider.provide().use { realm ->
            realm.executeTransaction {
                val task = realm.where(TaskDb::class.java).equalTo("title", data.title)
                    .findAll()
                task.deleteAllFromRealm()
            }
        }
    }
}