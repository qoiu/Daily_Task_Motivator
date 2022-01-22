package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.presentation.CategoryToPresentationMapper
import com.qoiu.dailytaskmotivator.presentation.TaskToPresentationMapper
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategoryToCategoryMapper
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategoryToTaskMapper

@Suppress("UNCHECKED_CAST")
class TaskModelFactory(
    private val taskInteractor: TaskInteractor,
    private val categoryInteractor: CategoriesInteractor
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TaskModel(
            taskInteractor,categoryInteractor,
            CategoryToPresentationMapper(),
            TaskToPresentationMapper(),
            TaskWithCategoryToTaskMapper(),
            TaskWithCategoryToCategoryMapper()
        ) as T
}