package com.qoiu.dailytaskmotivator.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CategoryDb(
    @PrimaryKey
    var title: String = "",
    var expand: Boolean=true,
    var color: Long = 0L
): RealmObject() {
}