package com.qoiu.dailytaskmotivator.data.category

import com.qoiu.dailytaskmotivator.UpdatableRealm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CategoryDb(
    @PrimaryKey
    var title: String = "",
    var expand: Boolean=true,
    var color: String = ""
): RealmObject(),UpdatableRealm<CategoryDb>{
    override fun update(data: CategoryDb) {
    }
}