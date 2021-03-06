package com.qoiu.dailytaskmotivator.presentation

import android.view.View
import kotlin.math.min

sealed class Structure {
    open fun same(other: Any?): Boolean = equals(other)
    open fun sameTitle(other: Any?): Boolean = false

    class Task(
        val title: String,
        val body: String = "",
        val reward: Int = 0,
        val expired: Long = 0L,
        val deadline: Long = 0L,
        val progressMax: Int = 0,
        currentProgress: Int = 0,
        val dailyTask: Boolean = false,
        val reusable: Boolean = false,
        val category: String = "",
        val color: String = "#FFFFFFFF"
    ) : Structure() {

        private val map = HashMap<Attr, Boolean>()
        val currentProgress = currentProgress
            get() = min(field, progressMax)

        init {
            map[Attr.BODY] = body != ""
            map[Attr.EXPIRED] = !(expired == 0L || dailyTask || reusable)
            map[Attr.DEADLINE] = !(deadline == 0L || dailyTask || reusable)
            map[Attr.PROGRESS] = (progressMax > 0)
        }

        fun isVisible(attr: Attr) =
            if (map[attr]!!) {
                View.VISIBLE
            } else {
                View.GONE
            }


        override fun toString(): String {
            return "Task(title='$title',\n category='$category')"
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + body.hashCode()
            result = 31 * result + reward
            result = 31 * result + expired.hashCode()
            result = 31 * result + deadline.hashCode()
            result = 31 * result + progressMax
            result = 31 * result + currentProgress
            result = 31 * result + dailyTask.hashCode()
            result = 31 * result + reusable.hashCode()
            result = 31 * result + category.hashCode()
            result = 31 * result + color.hashCode()
            return result
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Task

            if (title != other.title) return false
            if (body != other.body) return false
            if (reward != other.reward) return false
            if (expired != other.expired) return false
            if (deadline != other.deadline) return false
            if (progressMax != other.progressMax) return false
            if (currentProgress != other.currentProgress) return false
            if (dailyTask != other.dailyTask) return false
            if (reusable != other.reusable) return false
            if (category != other.category) return false
            if (color != other.color) return false

            return true
        }

        fun update(
            body: String? = null,
            reward: Int? = null,
            expired: Long? = null,
            deadline: Long? = null,
            progressMax: Int? = null,
            currentProgress: Int? = null,
            dailyTask: Boolean? = null,
            reusable: Boolean? = null,
            category: String? = null,
            color: String? = null
        ) = Task(
            this.title,
            body ?: this.body,
            reward ?: this.reward,
            expired ?: this.expired,
            deadline ?: this.deadline,
            progressMax ?: this.progressMax,
            currentProgress ?: this.currentProgress,
            dailyTask ?: this.dailyTask,
            reusable ?: this.reusable,
            category ?: this.category,
            color ?: this.color
        )


        companion object {
            enum class Attr {
                BODY, EXPIRED, DEADLINE, PROGRESS
            }
        }
    }

    class Category(
        val title: String,
        val expand: Boolean = true,
        val color: String = "#FFFFFFFF"
    ) : Structure() {

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + color.hashCode()
            result = 31 * result + expand.hashCode()
            return result
        }

        override fun toString(): String {
            return "Category(title='$title', expand=$expand)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Category

            if (title != other.title) return false
            if (color != other.color) return false
            if (expand != other.expand) return false

            return true
        }

        override fun same(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Category

            if (title != other.title) return false
            if (color != other.color) return false

            return true
        }

        override fun sameTitle(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Category
            if (title != other.title) return false
            return true
        }

        fun update(
            expand: Boolean? = null,
            color: String? = null
        ) = Category(
            title,
            expand ?: this.expand,
            color ?: this.color
        )
    }

    class CategoryWithTask(
        val category: Category,
        val tasks: List<Task>
    ) : Structure() {
        fun addTask(task: Task) =
            CategoryWithTask(this.category, buildList {
                this.addAll(tasks)
                this.add(task)
            })

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as CategoryWithTask

            if (category != other.category) return false
            if (tasks != other.tasks) return false

            return true
        }

        override fun same(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as CategoryWithTask

            if (!category.same(other.category)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = category.hashCode()
            result = 31 * result + tasks.hashCode()
            return result
        }

        override fun sameTitle(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as CategoryWithTask

            if (!category.sameTitle(other.category)) return false
            return true
        }

        override fun toString(): String = "CategoryWithTask(category=$category, tasks=$tasks)"
    }

    class NewTask(private val title: String) : Structure() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NewTask

            if (title != other.title) return false

            return true
        }

        override fun hashCode(): Int {
            return title.hashCode()
        }
    }
}