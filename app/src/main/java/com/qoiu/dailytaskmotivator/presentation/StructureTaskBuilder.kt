package com.qoiu.dailytaskmotivator.presentation

import com.qoiu.dailytaskmotivator.Builder
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import kotlin.math.min

class StructureTaskBuilder(private val stringProvider: ResourceProvider.StringProvider) : Builder<Structure.Task> {
    private var title: String = ""
    private var body: String = ""
    private var reward: Int = 0
    private var expired: Long = 0L
    private var deadline: Long = 0L
    private var progressMax: Int = 0
    private var currentProgress: Int = 0
    private var dailyTask: Boolean = false
    private var reusable: Boolean = false
    private var category: String = ""
    private var color: String = "#FFFFFFFF"

    fun title(value: String): StructureTaskBuilder{
        title = value
        return this
    }

    fun description(value: String): StructureTaskBuilder{
        body = value
        return this
    }

    fun reward(value: Int): StructureTaskBuilder{
        reward = value
        return this
    }

    fun reward(value: String): StructureTaskBuilder{
        if (value == "")
            throw IllegalStateException(stringProvider.string(R.string.error_reward))
        try {
            return reward(value.toInt())
        } catch (e: Exception) {
            throw IllegalStateException(stringProvider.string(R.string.error_reward_incorrect))
        }
    }

    fun expired(value: Long): StructureTaskBuilder{
        expired = value
        return this
    }

    fun expired(value: String): StructureTaskBuilder =
        if (value == "") {
            expired(0L)
        } else {
            try {
                expired(TaskCalendar().formatFromString(value))
            } catch (e: Exception) {
                throw IllegalStateException(stringProvider.string(R.string.error_expired))
            }
        }

    fun deadline(value: Long) : StructureTaskBuilder{
        deadline = value
        return this
    }

    fun deadline(value: String) : StructureTaskBuilder=
        if (value == "") {
            deadline(0L)
        } else {
            try {
                deadline(TaskCalendar().formatFromString(value))
            } catch (e: Exception) {
                throw IllegalStateException(stringProvider.string(R.string.error_expired))
            }
        }

    fun progress(value: Int): StructureTaskBuilder{
        progressMax = value
        return this
    }

    fun progress(value: String): StructureTaskBuilder =
       if (value == "") {
           progress(0)
       } else {
           try {
               progress(value.toInt())
           } catch (e: Exception) {
               throw IllegalStateException(stringProvider.string(R.string.error_progress))
           }
       }

    fun currentProgress(value: Int): StructureTaskBuilder{
        currentProgress = value
        return this
    }

    fun dailyTask(value: Boolean): StructureTaskBuilder{
        dailyTask = value
        return this
    }

    fun reusable(value: Boolean): StructureTaskBuilder{
        reusable = value
        return this
    }

    fun category(value: String): StructureTaskBuilder{
        category = value
        return this
    }

    fun color(value: String): StructureTaskBuilder{
        color = value
        return this
    }

    override fun built() = Structure.Task(
        title,
        body,
        reward,
        if(dailyTask||reusable) 0 else expired,
        if(dailyTask||reusable) 0 else deadline,
        progressMax,
        min(currentProgress, progressMax),
        dailyTask,
        reusable,
        category,
        color
    )
}