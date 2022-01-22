package com.qoiu.dailytaskmotivator

import android.content.Context
import com.qoiu.dailytaskmotivator.data.category.CategoryDataSource
import com.qoiu.dailytaskmotivator.data.category.CategoryDbToCategoryMapper
import com.qoiu.dailytaskmotivator.data.category.CategoryRepository
import com.qoiu.dailytaskmotivator.data.category.CategoryToCategoryDbMapper
import com.qoiu.dailytaskmotivator.data.task.TaskDataSource
import com.qoiu.dailytaskmotivator.data.task.TaskDbToTaskMapper
import com.qoiu.dailytaskmotivator.data.task.TaskRepository
import com.qoiu.dailytaskmotivator.data.task.TaskToTaskDbMapper
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor

class CoreModule {

    lateinit var sharedProvider: ResourceProvider.Shared
    lateinit var taskInteractor: TaskInteractor
    lateinit var categoryInteractor: CategoriesInteractor

    fun init(context: Context) {
        sharedProvider = ResourceProvider.Shared(context)
        val stringProvider = ResourceProvider.String(context)
        val realmProvider = RealmProvider.Base(context)
        val taskRealmSource = TaskDataSource(realmProvider)
        val categoryRealmSource = CategoryDataSource(realmProvider)
        val taskRepository = TaskRepository(taskRealmSource, TaskDbToTaskMapper(),TaskToTaskDbMapper())
        val categoryRepository = CategoryRepository(categoryRealmSource, CategoryDbToCategoryMapper(),CategoryToCategoryDbMapper())
        taskInteractor = TaskInteractor.Base(taskRepository,stringProvider)
        categoryInteractor = CategoriesInteractor.Base(categoryRepository)
    }
}