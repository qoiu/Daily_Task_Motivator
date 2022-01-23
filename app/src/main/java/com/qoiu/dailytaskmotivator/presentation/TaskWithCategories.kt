package com.qoiu.dailytaskmotivator.presentation

sealed class TaskWithCategories {
    open fun compareAll(other: Any?): Boolean = true
    class Task(
        val title: String,
        val body: String = "",
        var reward: Int = 0,
        var expired: Long = 0L,
        val deadline: Long = 0L,
        val progressMax: Int = 0,
        var currentProgress: Int = 0,
        val dailyTask: Boolean = false,
        val reusable: Boolean = false,
        var category: String="",
        var color: String="#FFFFFFFF"
        // TODO: 22.01.2022   val visibility: HashMap<String,>
    ) : TaskWithCategories() {

        override fun compareAll(other: Any?): Boolean {
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

        override fun toString(): String {
            return "Task(title='$title', category='$category')"
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

            return true
        }


    }

    class Category(
        val title: String,
        val expand: Boolean=true,
        val color: String="#FFFFFFFF"
    ): TaskWithCategories(){
        override fun compareAll(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Category

            if (title != other.title) return false
            if (color != other.color) return false
            if (expand != other.expand) return false

            return true
        }


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

            return true
        }
    }

    class Empty(): TaskWithCategories()
}