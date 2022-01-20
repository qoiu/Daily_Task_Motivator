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
            Realm.setDefaultConfiguration(getConfig(name))
        }

        private fun getConfig(name: String) = RealmConfiguration.Builder()
            .schemaVersion(SCHEMA_VERSION)
            .migration(MyMigrations(name))
            .name(name)
            .build()

        override fun provide(): Realm = Realm.getInstance(getConfig(name))

        private companion object {
            const val SCHEMA_VERSION = 1L
        }
    }

    class Base(context: Context) : RealmProvider.Abstract(context, "Tasks")
}