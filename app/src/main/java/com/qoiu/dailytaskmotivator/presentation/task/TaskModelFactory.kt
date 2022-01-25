package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.presentation.CategoryToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.TaskToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.StructureToCategoryMapper
import com.qoiu.dailytaskmotivator.presentation.StructureToTaskMapper

@Suppress("UNCHECKED_CAST")
class TaskModelFactory(
    private val taskInteractor: TaskInteractor,
    private val categoryInteractor: CategoriesInteractor,
    private val stringProvider: ResourceProvider.StringProvider
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TaskModel(
            taskInteractor,categoryInteractor,
            CategoryToStructureMapper(),
            TaskToStructureMapper(stringProvider),
            StructureToTaskMapper(stringProvider),
            StructureToCategoryMapper(),
            stringProvider
        ) as T
}