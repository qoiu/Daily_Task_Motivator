package com.qoiu.dailytaskmotivator.presentation.utils

import com.qoiu.dailytaskmotivator.domain.entities.Category
import com.qoiu.dailytaskmotivator.domain.entities.Task
import com.qoiu.dailytaskmotivator.presentation.CategoryToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.Structure
import com.qoiu.dailytaskmotivator.presentation.TaskToStructureMapper
import org.junit.Assert.*
import org.junit.Test

class CategoryWithTaskGeneratorTest{

    private val categoryA = Category("a", false)
    private val categoryAExp = Category("a", true)
    private val categoryB = Category("b", false)
    private val categoryBExp = Category("b", true)
    private val categoryC = Category("c", false)
    private val categoryCExp = Category("c", true)
    private val categoryD = Category("d", false)
    private val categoryDExp = Category("d", true)
    private val categoryOther = Category("Other", true)
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
    private val finalItem = Structure.NewTask("newTask")

    private val taskMapper = TaskToStructureMapper(TestStringProvider("Other"))
    private val catMapper = CategoryToStructureMapper()
    private val stringProvider = TestStringProvider("newTask")

    private val testTasks = listOf(tasks[12],tasks[7], tasks[6], tasks[0], tasks[1], tasks[10], tasks[3])

    private fun category(category: Category) = catMapper.map(category)
    private fun task(id: Int) = taskMapper.map(tasks[id])
    private fun withTask(category: Category, list: List<Structure.Task> = emptyList()) = Structure.CategoryWithTask(
        category(category),list
    )

    @Test
    fun simple_all_closed() {
        val testCategory = listOf(categoryA, categoryB, categoryC, categoryD)
        val actual = CategoryWithTaskGenerator(
            testTasks, testCategory,
            catMapper, taskMapper,stringProvider
        ).execute()
        val expected = listOf(
            withTask(categoryD),withTask(categoryC),
            withTask(categoryB),withTask(categoryA),
            withTask(categoryOther, listOf(task(12))),finalItem)
        assertEquals(expected,actual)
    }

    @Test
    fun simple_c_expand(){
        val testCategory = listOf(categoryA, categoryB, categoryCExp, categoryD)
        val actual = CategoryWithTaskGenerator(
            testTasks, testCategory,
            catMapper, taskMapper,stringProvider
        ).execute()
        val expected = listOf(
            withTask(categoryD),withTask(categoryCExp, listOf(task(7), task(6))),
            withTask(categoryB),withTask(categoryA),
            withTask(categoryOther, listOf(task(12))),finalItem)
        assertEquals(expected,actual)
    }

    @Test
    fun simple_c_d_expand(){
        val categories = listOf(categoryA, categoryB, categoryCExp, categoryDExp)
        val actual = CategoryWithTaskGenerator(
            testTasks, categories,
            catMapper, taskMapper,stringProvider
        ).execute()
        val expected = listOf(
            withTask(categories[3], listOf(task(10))),
            withTask(categories[2], listOf(task(7), task(6))),
            withTask(categories[1]),withTask(categories[0]),
            withTask(categoryOther, listOf(task(12))),finalItem)
        assertEquals(expected,actual)
    }

    @Test
    fun test_without_categories(){
        val categories = listOf(categoryAExp, categoryBExp, categoryCExp, categoryDExp)
        val actual = CategoryWithTaskGenerator(
            testTasks, emptyList(),
            catMapper, taskMapper,stringProvider
        ).execute()
        val expected = listOf(
            withTask(categories[3], listOf(task(10))),
            withTask(categories[2], listOf(task(7), task(6))),
            withTask(categories[1], listOf(task(3))),
            withTask(categories[0], listOf(task(0),task(1))),
            withTask(categoryOther, listOf(task(12))),finalItem)
        assertEquals(expected,actual)

    }
}