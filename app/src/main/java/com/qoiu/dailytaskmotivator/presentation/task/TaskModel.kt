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
    private val taskDomainMapper: TaskWithCategoryToTaskMapper
    ) :
    BaseViewModel<List<TaskWithCategories>>(TaskCommunication()) {

    fun saveTask(task: TaskWithCategories.Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskInteractor.save(taskDomainMapper.map(task))
            categoryInteractor.saveCategory(Category(task.category))
        }.invokeOnCompletion {
            updateData()
        }
    }

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = taskInteractor.loadTask()
            val categories = categoryInteractor.loadCategories()
            val list = mutableListOf<TaskWithCategories>()
            if(categories.isEmpty()){
                tasks.forEach{list.add(taskMapper.map(it))}
            }else{
                var category: Category = categories[0]
                tasks.forEach {
                    if(it.category!=category.title){
                        list.add(categoryMapper.map(category))
                    }
                    list.add(taskMapper.map(it))
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