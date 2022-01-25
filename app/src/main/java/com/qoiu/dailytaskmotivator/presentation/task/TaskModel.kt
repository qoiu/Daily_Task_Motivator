package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.*
import com.qoiu.dailytaskmotivator.presentation.utils.CategoryWithTaskGenerator
import com.qoiu.dailytaskmotivator.presentation.utils.ListWithCategoriesGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskModel(
    private val taskInteractor: TaskInteractor,
    private val categoryInteractor: CategoriesInteractor,
    private val categoryMapper: CategoryToStructureMapper,
    private val taskMapper: TaskToStructureMapper,
    private val taskDomainMapper: StructureToTaskMapper,
    private val categoryDomainMapper: StructureToCategoryMapper,
    private val stringProvider: ResourceProvider.StringProvider
) : BaseViewModel<List<Structure>>(TaskCommunication()) {

    private var portraitOrientation = true
    fun saveTask(data: Structure) {
        viewModelScope.launch(Dispatchers.IO) {
            if (data is Structure.Task) {
                taskInteractor.save(
                    taskDomainMapper.map(data)
                )
                val categories =
                    categoryInteractor.loadCategories().find { it.title == data.category }
                categories ?: categoryInteractor.saveCategory(
                    Category(data.category, color = data.color)
                )
            } else if (data is Structure.Category) {
                categoryInteractor.saveCategory(categoryDomainMapper.map(data))
            }
        }.invokeOnCompletion {
            updateData()
        }
    }

    private fun updateData() = updateData(portraitOrientation)

    fun updateData(portraitOrientation: Boolean) {
        lateinit var tasks: List<Task>
        lateinit var categories: List<Category>
        this.portraitOrientation = portraitOrientation
        viewModelScope.launch(Dispatchers.IO) {
            tasks = taskInteractor.loadTask()
            categories = categoryInteractor.loadCategories()
        }.invokeOnCompletion {
            communication.provide(
                if (portraitOrientation)
                    ListWithCategoriesGenerator(
                        tasks,
                        categories,
                        categoryMapper,
                        taskMapper,
                        stringProvider
                    ).execute()
                else
                    CategoryWithTaskGenerator(
                        tasks,
                        categories,
                        categoryMapper,
                        taskMapper,
                        stringProvider
                    ).execute()
            )
        }
    }

    fun deleteTask(task: Structure.Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskInteractor.removeTask(taskDomainMapper.map(task))
        }.invokeOnCompletion { updateData() }
    }

}