package com.qoiu.dailytaskmotivator.presentation.task

import androidx.lifecycle.viewModelScope
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
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
        lateinit var tasks: List<Task>
        lateinit var categories: List<Category>
        viewModelScope.launch(Dispatchers.IO) {
            tasks = taskInteractor.loadTask().sortedByDescending { it.category }
            categories = categoryInteractor.loadCategories().sortedByDescending { it.title }
        }.invokeOnCompletion {
            communication.provide(
                ListWithCategoriesGenerator(tasks, categories, categoryMapper, taskMapper).execute()
            )
        }
    }

    fun deleteTask(task: TaskWithCategories.Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskInteractor.removeTask(taskDomainMapper.map(task))
        }.invokeOnCompletion { updateData() }
    }

}