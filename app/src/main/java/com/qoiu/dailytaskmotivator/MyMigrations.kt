package com.qoiu.dailytaskmotivator

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class MyMigrations(private val version: Long) : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
        var prevVersion = oldVersion
        if (prevVersion == 1L) {
            val new = schema.get("TaskDb")
            new?.addField("reusable", Boolean::class.java)
            prevVersion++
        }

        if (prevVersion == 2L) {
            schema.get("TaskDb")?.let {
                if (!it.hasField("category"))
                    it.addField("category", String::class.java, FieldAttribute.REQUIRED)
                prevVersion++
            }
        }

        if (prevVersion == 3L) {
            schema.create("CategoryDb").apply {
                addField(
                    "title",
                    String::class.java,
                    FieldAttribute.PRIMARY_KEY,
                    FieldAttribute.REQUIRED
                )
                addField("expand", Boolean::class.java, FieldAttribute.REQUIRED)
                addField("color", Long::class.java, FieldAttribute.REQUIRED)
            }
            prevVersion++
        }

        if(prevVersion == 4L){
            schema.get("CategoryDb")?.let {
                it.removeField("color")
                it.addField("color", String::class.java, FieldAttribute.REQUIRED)
                prevVersion++
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is MyMigrations)
            return this.version == other.version
        return false
    }

    override fun hashCode(): Int {
        return version.toInt()
    }
}