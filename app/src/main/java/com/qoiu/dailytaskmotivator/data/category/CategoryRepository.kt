package com.qoiu.dailytaskmotivator.data.category

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.data.AbstractRepository
import com.qoiu.dailytaskmotivator.data.RealmDataSource
import com.qoiu.dailytaskmotivator.domain.entities.Category

class CategoryRepository(
    dataSource: RealmDataSource<CategoryDb>,
    categoryMapper: Mapper.Data<CategoryDb,Category>,
    categoryDbMapper: Mapper.Data<Category,CategoryDb>
): AbstractRepository<CategoryDb, Category>(dataSource,categoryMapper,categoryDbMapper)  {
}