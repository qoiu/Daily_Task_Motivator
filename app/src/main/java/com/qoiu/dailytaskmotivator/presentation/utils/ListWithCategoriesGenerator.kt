package com.qoiu.dailytaskmotivator.presentation.utils

import com.qoiu.dailytaskmotivator.Execute
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.CategoryToPresentationMapper
import com.qoiu.dailytaskmotivator.presentation.TaskToPresentationMapper
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class ListWithCategoriesGenerator(
    private val unsortedTasks: List<Task>,
    private val unsortedCategories: List<Category>,
    private val categoryMapper: CategoryToPresentationMapper,
    private val taskMapper: TaskToPresentationMapper,
    private val stringProvider: ResourceProvider.StringProvider
) : Execute<List<TaskWithCategories>> {

    override fun execute(): List<TaskWithCategories> {
        val tasks = unsortedTasks.sortedByDescending { it.category }
        val categories = mutableListOf<Category>()
        categories.addAll(unsortedCategories.sortedByDescending { it.title })
        val list = mutableListOf<TaskWithCategories>()
        if (categories.size == 0) categories.add(Category("test"))
        var categoryId = 0
        tasks.forEach { task ->
            if (task.category == "") {
                list.add(taskMapper.map(task))
            } else {
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
                    list.add(taskMapper.map(task))
                }
            }
        }
        list.add(TaskWithCategories.NewTask(stringProvider.string(R.string.new_task)))
        return list
    }

    private fun containsCategory(list: List<TaskWithCategories>, category: String): Boolean {
        list.forEach {
            if (it is TaskWithCategories.Category && it.title == category) return true
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