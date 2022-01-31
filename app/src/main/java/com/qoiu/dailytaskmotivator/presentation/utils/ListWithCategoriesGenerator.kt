package com.qoiu.dailytaskmotivator.presentation.utils

import com.qoiu.dailytaskmotivator.Execute
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.CategoryToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.TaskToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.Structure

class ListWithCategoriesGenerator(
    private val unsortedTasks: List<Task>,
    private val unsortedCategories: List<Category>,
    private val categoryMapper: CategoryToStructureMapper,
    private val taskMapper: TaskToStructureMapper,
    private val stringProvider: ResourceProvider.StringProvider
) : Execute<List<Structure>> {

    override fun execute(): List<Structure> {
        val tasks = unsortedTasks.sortedByDescending { it.category }.map { taskMapper.map(it) }
        val categories = mutableListOf<Category>()
        categories.addAll(unsortedCategories.sortedByDescending { it.title })
        val list = mutableListOf<Structure>()
        if (categories.size == 0) categories.add(Category("test"))
        var categoryId = 0
        tasks.forEach { task ->
            if (task.category != categories[categoryId].title) {
                categoryId = getCategoryId(categories, task.category)
                if (categoryId < 0) {
                    categories.add(Category(task.category))
                    categories.sortByDescending { it.title }
                    categoryId = getCategoryId(categories, task.category)
                }
                list.add(categoryMapper.map(categories[categoryId]))
            } else if (!containsCategory(list, categories[categoryId].title)) {
                list.add(categoryMapper.map(categories[categoryId]))
            }
            if (categories[categoryId].expand) {
                list.add(task)
            }
        }
        list.add(Structure.NewTask(stringProvider.string(R.string.new_task)))
        return list
    }

    private fun containsCategory(list: List<Structure>, category: String): Boolean {
        list.forEach {
            if (it is Structure.Category && it.title == category) return true
        }
        return false
    }

    private fun getCategoryId(list: List<Category>, category: String): Int {
        for (i in list.indices) {
            if (list[i].title == category) return i
        }
        return -1
    }
}