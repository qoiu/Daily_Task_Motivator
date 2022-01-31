package com.qoiu.dailytaskmotivator.presentation.utils

import com.qoiu.dailytaskmotivator.Execute
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.CategoryToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.Structure
import com.qoiu.dailytaskmotivator.presentation.TaskToStructureMapper

class CategoryWithTaskGenerator(
    private val unsortedTasks: List<Task>,
    private val unsortedCategories: List<Category>,
    private val categoryMapper: CategoryToStructureMapper,
    private val taskMapper: TaskToStructureMapper,
    private val stringProvider: ResourceProvider.StringProvider
) : Execute<List<Structure>> {

    override fun execute(): List<Structure> {
        val tasks = unsortedTasks.sortedByDescending { it.category }.map { taskMapper.map(it) }
        val categories = mutableListOf<Structure.Category>()
        categories.addAll(unsortedCategories
            .sortedByDescending { it.title }
            .map { categoryMapper.map(it) }
        )
        val list = mutableListOf<Structure.CategoryWithTask>()
        tasks.forEach { task ->
            val category = categories.find {
                it.title == task.category
            }
            if (category == null) {
                val newCategory = Structure.Category(task.category, color = task.color)
                categories.add(newCategory)
                list.add(Structure.CategoryWithTask(newCategory, listOf(task)))
            } else {
                var categoryWithTask = list.find { catWithTask ->
                    catWithTask.category.sameTitle(category)
                }
                if (categoryWithTask == null) {
                    categoryWithTask = Structure.CategoryWithTask(category, emptyList())
                    list.add(categoryWithTask)
                }
                if (category.expand) {
                    list.remove(categoryWithTask)
                    list.add(categoryWithTask.addTask(task))
                }
            }
        }
        return buildList {
            this.addAll(list)
            this.add(Structure.NewTask(stringProvider.string(R.string.new_task)))
        }
    }
}