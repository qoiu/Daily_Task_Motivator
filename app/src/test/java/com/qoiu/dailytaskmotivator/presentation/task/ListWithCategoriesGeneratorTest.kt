package com.qoiu.dailytaskmotivator.presentation.task

import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.CategoryToPresentationMapper
import com.qoiu.dailytaskmotivator.presentation.TaskToPresentationMapper
import org.junit.Assert.*

import org.junit.Test

class ListWithCategoriesGeneratorTest {

    private val categoryA = Category("a", false)
    private val categoryAExp = Category("a", true)
    private val categoryB = Category("b", false)
    private val categoryBExp = Category("b", true)
    private val categoryC = Category("c", false)
    private val categoryCExp = Category("c", true)
    private val categoryD = Category("d", false)
    private val categoryDExp = Category("d", true)
    private val tasks = listOf(
        Task("a1", category = "a"),//0
        Task("a2", category = "a"),//1
        Task("a3", category = "a"),//2
        Task("b1", category = "b"),//3
        Task("b2", category = "b"),//4
        Task("b3", category = "b"),//5
        Task("c1", category = "c"),//6
        Task("c2", category = "c"),//7
        Task("c3", category = "c"),//8
        Task("d1", category = "d"),//9
        Task("d2", category = "d"),//10
        Task("d3", category = "d"),//11
        Task("e1", category = ""),//12
        Task("e2", category = "")//13
    )

    private val taskMapper = TaskToPresentationMapper()
    private val catMapper = CategoryToPresentationMapper()
    private fun tasksWithCategory() = tasks.forEach { taskMapper.map(it) }

    private val testTasks = listOf(tasks[12],tasks[7], tasks[6], tasks[0], tasks[1], tasks[10], tasks[3])

    private fun category(category: Category) = catMapper.map(category)
    private fun task(id: Int) = taskMapper.map(tasks[id])
    @Test
    fun simple_all_closed() {
        val testCategory = listOf(categoryA, categoryB, categoryC, categoryD)
        val actual = ListWithCategoriesGenerator(
            testTasks, testCategory,
            CategoryToPresentationMapper(), TaskToPresentationMapper()
        ).execute()
        val expected = listOf(category(categoryD),category(categoryC),category(categoryB),category(categoryA),task(12))
        assertEquals(expected,actual)
    }

    @Test
    fun simple_c_expand(){
        val testCategory = listOf(categoryA, categoryB, categoryCExp, categoryD)
        val actual = ListWithCategoriesGenerator(
            testTasks, testCategory,
            CategoryToPresentationMapper(), TaskToPresentationMapper()
        ).execute()
        val expected = listOf(category(categoryD),category(categoryCExp),task(7), task(6),category(categoryB),category(categoryA),task(12))
        assertEquals(expected,actual)
    }

    @Test
    fun simple_c_d_expand(){
        val categories = listOf(categoryA, categoryB, categoryCExp, categoryDExp)
        val actual = ListWithCategoriesGenerator(
            testTasks, categories,
            CategoryToPresentationMapper(), TaskToPresentationMapper()
        ).execute()
        val expected = listOf(category(categories[3]),task(10),category(categories[2]),task(7), task(6),category(categories[1]),category(categories[0]),task(12))
        assertEquals(expected,actual)
    }

    @Test
    fun test_without_categories(){
        val categories = listOf(categoryA, categoryB, categoryCExp, categoryDExp)
        val actual = ListWithCategoriesGenerator(
            testTasks, emptyList(),
            CategoryToPresentationMapper(), TaskToPresentationMapper()
        ).execute()
        val expected = listOf(
            category(categories[3]),task(10),
            category(categories[2]),task(7), task(6),
            category(categories[1]),task(3),
            category(categories[0]),task(0),task(1),
            task(12))
        assertEquals(expected,actual)

    }
}
