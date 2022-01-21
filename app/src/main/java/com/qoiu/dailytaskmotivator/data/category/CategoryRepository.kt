package com.qoiu.dailytaskmotivator.data.category

import com.qoiu.dailytaskmotivator.data.AbstractRepository
import com.qoiu.dailytaskmotivator.data.RealmDataSource

class CategoryRepository(dataSource: RealmDataSource<CategoryDb>): AbstractRepository<CategoryDb>(dataSource)  {
}