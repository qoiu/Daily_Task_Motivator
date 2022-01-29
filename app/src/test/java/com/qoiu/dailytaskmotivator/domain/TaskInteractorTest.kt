package com.qoiu.dailytaskmotivator.domain

import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.entities.DefaultTasks
import com.qoiu.dailytaskmotivator.domain.entities.Task
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import java.lang.IllegalStateException

class TaskInteractorTest {
    private val strProvider = object : ResourceProvider.StringProvider{
        override fun string(id: Int): String ="defaultTask"
    }

    private val simpleTask = Task("simpleTask", reward = 10)
    private val emptyTitle = Task("", reward = 10)
    private val emptyReward = Task("emptyReward", reward = 0)
    private val deadline = Task("simpleTask", reward = 10)
    private val expiredTask = Task("expiredTask", reward = 10, expired = 1000)

    @Test
    fun getDefaultList(): Unit = runBlocking{
        val expected = DefaultTasks(strProvider).getDefault()
        val testRepository = TestRepository(expected)
        val actual = TaskInteractor.Base(testRepository,strProvider).loadTask()
        assertEquals(expected,actual)
    }

    @Test(expected = IllegalStateException::class)
    fun test(): Unit = runBlocking {
        val testRepository = TestRepository()
        TaskInteractor.Base(testRepository,strProvider).loadTask()
    }

    @Test
    fun simpleTestLoadTask(): Unit = runBlocking{
        val actualTaskList = listOf(
            simpleTask,
            emptyTitle,
            emptyReward,
            expiredTask
        )
        val expected = listOf(simpleTask)

        val testRepository = TestRepository(actualTaskList,emptyTitle,emptyReward,expiredTask)
        val actual = TaskInteractor.Base(testRepository,strProvider).loadTask()
        assertEquals(expected,actual)
    }

    @Test
    fun simpleTest(): Unit = runBlocking{
        val actualTaskList = listOf(
            simpleTask
        )
        val expected = listOf(simpleTask)

        val testRepository = TestRepository(actualTaskList,emptyTitle,emptyReward,expiredTask)
        val actual = TaskInteractor.Base(testRepository,strProvider).loadTask()
        assertEquals(expected,actual)
    }

    @Test
    fun logicLoadTask(): Unit = runBlocking {

    }

    @Test
    fun removeTask() = runBlocking {
        val actualTaskList = listOf(simpleTask)
        val testRepository = TestRepository(actualTaskList,simpleTask)
        TaskInteractor.Base(testRepository,strProvider).removeTask(simpleTask)
    }

    @Test
    fun save() = runBlocking{
        val actualTaskList = listOf(simpleTask)
        val testRepository = TestRepository(actualTaskList,simpleTask)
        TaskInteractor.Base(testRepository,strProvider).save(simpleTask)
    }
}

class TestRepository(private val taskList: List<Task> = emptyList(),private vararg val expected: Task= arrayOf()) :
    Repository<Task> {
    override suspend fun save(data: Task) {
        var test = false
        expected.forEach { task ->
            if(data==task){
                assertEquals(data,task)
                test = true
            }
        }
        if(!test)throw IllegalStateException("No data in expected in method save")
    }

    override suspend fun fetchData(): List<Task> = taskList

    override suspend fun remove(data: Task) {
        var test = false
        expected.forEach { task ->
            if(data==task){
                assertEquals(data,task)
                test = true
            }
        }
        if(!test)throw IllegalStateException("No data in expected in method remove")

    }
}