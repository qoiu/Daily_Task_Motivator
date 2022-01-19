package com.qoiu.dailytaskmotivator

import io.realm.DynamicRealm
import io.realm.RealmMigration

class MyMigrations : RealmMigration{
        override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
            val schema = realm.schema
        }
}