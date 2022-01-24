package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.*
import com.qoiu.dailytaskmotivator.presentation.utils.ListWithCategoriesGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskModel(
    private val taskInteractor: TaskInteractor,
    private val categoryInteractor: CategoriesInteractor,
    private val categoryMapper: CategoryToPresentationMapper,
    private val taskMapper: TaskToPresentationMapper,
    private val taskDomainMapper: TaskWithCategoryToTaskMapper,
    private val categoryDomainMapper: TaskWithCategoryToCategoryMapper,
    private val stringProvider: ResourceProvider.StringProvider
) : BaseViewModel<List<TaskWithCategories>>(TaskCommunication()) {

    fun saveTask(data: TaskWithCategories) {
        viewModelScope.launch(Dispatchers.IO) {
            if (data is TaskWithCategories.Task) {
                taskInteractor.save(
                    taskDomainMapper.map(data)
                )
                val categories =
                    categoryInteractor.loadCategories().find { it.title == data.category }
                categories ?: categoryInteractor.saveCategory(Category(data.category,color = data.color))
            }
            if (data is TaskWithCategories.Category) {
                categoryInteractor.saveCategory(categoryDomainMapper.map(data))
            }
        }.invokeOnCompletion {
            updateData()
        }
    }

    fun updateData() {
        lateinit var tasks: List<Task>
        lateinit var categories: List<Category>
        viewModelScope.launch(Dispatchers.IO) {
            tasks = taskInteractor.loadTask()
            categories = categoryInteractor.loadCategories()
        }.invokeOnCompletion {
            communication.provide(
                ListWithCategoriesGenerator(tasks, categories, categoryMapper, taskMapper,stringProvider).execute()
            )
        }
    }

    fun deleteTask(task: TaskWithCategories.Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskInteractor.removeTask(taskDomainMapper.map(task))
        }.invokeOnCompletion { updateData() }
    }

}