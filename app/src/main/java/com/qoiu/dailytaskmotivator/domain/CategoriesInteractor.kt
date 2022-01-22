package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.domain.entities.Category

interface CategoriesInteractor {
    suspend fun loadCategories(): List<Category>
    suspend fun saveCategory(category: Category)

    class Base(
        private val repository: Repository<Category>
    ): CategoriesInteractor{
        override suspend fun loadCategories(): List<Category>{
            return repository.fetchData()
        }

        override suspend fun saveCategory(category: Category) {
            repository.save(category)
        }

    }
}