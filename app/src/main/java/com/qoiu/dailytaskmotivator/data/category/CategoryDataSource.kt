package com.qoiu.dailytaskmotivator.data.category

import com.qoiu.dailytaskmotivator.RealmProvider
import com.qoiu.dailytaskmotivator.data.AbstractDataSource
import com.qoiu.dailytaskmotivator.data.RealmDataSource
import io.realm.RealmAny

class CategoryDataSource(
    realmProvider: RealmProvider, override val primaryKey: String="title"
) : AbstractDataSource<CategoryDb>(realmProvider){
    override fun compareTo(data: CategoryDb): RealmAny = RealmAny.valueOf(data.title)

    override fun realmClass(): Class<CategoryDb> = CategoryDb::class.java
}