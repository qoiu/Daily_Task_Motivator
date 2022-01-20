package com.qoiu.dailytaskmotivator.data

import com.qoiu.dailytaskmotivator.*

interface RealmDataSource : Read<List<TaskDb>>, Save<TaskDb>, Remove<TaskDb>, Update<TaskDb> {

    class Base(
        private val realmProvider: RealmProvider
    ) : RealmDataSource {
        override fun read(): List<TaskDb> {
            realmProvider.provide().use { realm ->
                val task = realm.where(TaskDb::class.java)
                    .findAll() ?: emptyList<TaskDb>()
                return realm.copyFromRealm(task)
            }
        }

        override fun save(data: TaskDb) {
            realmProvider.provide().executeTransaction { realm ->
                realm.insert(data)
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

        override fun update(data: TaskDb) {
            realmProvider.provide().use { realm ->
                realm.executeTransaction {
                    val task = realm.where(TaskDb::class.java).equalTo("title", data.title)
                        .findFirst()
                    task?.update(data)?:it.insert(data)
                }
            }
        }
    }
}