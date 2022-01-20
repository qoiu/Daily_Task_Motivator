package com.qoiu.dailytaskmotivator

import android.util.Log
import io.realm.DynamicRealm
import io.realm.RealmMigration

class MyMigrations(private val name: String) : RealmMigration{
    private val version: Int =1
        override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
            val schema = realm.schema

            Log.i("MyMigration","old: $oldVersion, new: $newVersion")
            if (oldVersion == 1L) {
                val new = schema.get("TaskDb")
                new?.addField("reusable", Boolean::class.java)
                new?.transform {
                    it.hashCode()
                }
            }
        }

    override fun equals(other: Any?): Boolean {
        if(other is MyMigrations)
        return this.version == other.version
        return false
    }

    override fun hashCode(): Int {
        return version
    }
}