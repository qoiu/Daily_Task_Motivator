package com.qoiu.dailytaskmotivator

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

interface RealmProvider {
    fun provide(): Realm

    abstract class Abstract(
        context: Context,
        private val name: String
    ) : RealmProvider {

        init {
            Realm.init(context)
            val config = getConfig()
            Realm.setDefaultConfiguration(config)
        }

        private fun getConfig() = RealmConfiguration.Builder()
        .schemaVersion(SCHEMA_VERSION)
        .migration(MyMigrations(SCHEMA_VERSION))
        .name(name)
        .build()

        override fun provide(): Realm = Realm.getDefaultInstance()

        private companion object {
            const val SCHEMA_VERSION = 3L
        }
    }

    class Base(context: Context) : RealmProvider.Abstract(context, "Tasks")
}