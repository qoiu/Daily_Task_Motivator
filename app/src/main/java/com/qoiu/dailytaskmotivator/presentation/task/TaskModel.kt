package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.presentation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskModel(
    private val taskInteractor: TaskInteractor,
    private val categoryInteractor: CategoriesInteractor,
    private val categoryMapper: CategoryToPresentationMapper,
    private val taskMapper: TaskToPresentationMapper,
    private val taskDomainMapper: TaskWithCategoryToTaskMapper,
    private val categoryDomainMapper: TaskWithCategoryToCategoryMapper
) :
    BaseViewModel<List<TaskWithCategories>>(TaskCommunication()) {

    fun saveTask(data: TaskWithCategories) {
        viewModelScope.launch(Dispatchers.IO) {
            if (data is TaskWithCategories.Task) {
                taskInteractor.save(taskDomainMapper.map(data))
                categoryInteractor.saveCategory(Category(data.category))
            }
            if (data is TaskWithCategories.Category) {
                categoryInteractor.saveCategory(categoryDomainMapper.map(data))
            }
        }.invokeOnCompletion {
            updateData()
        }
    }

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = taskInteractor.loadTask().sortedByDescending { it.category }
            val categories = categoryInteractor.loadCategories().sortedByDescending { it.title }
            val list = mutableListOf<TaskWithCategories>()
            if (categories.isEmpty()) {
                tasks.forEach { list.add(taskMapper.map(it)) }
            } else {
                var category = Category("")
                tasks.forEach { task ->
                    if (task.category != category.title)
                        categories.forEach {
                            if (task.category == it.title && task.category != "") {
                                category = it
                                list.add(categoryMapper.map(it))
                            }
                        }
                    if (category.expand || task.category == "")
                        list.add(taskMapper.map(task))
                }
            }
            communication.provide(list)
        }
    }

    fun deleteTask(task: TaskWithCategories.Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskInteractor.removeTask(taskDomainMapper.map(task))
        }.invokeOnCompletion { updateData() }
    }

}