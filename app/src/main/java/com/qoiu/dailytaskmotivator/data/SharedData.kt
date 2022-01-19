package com.qoiu.dailytaskmotivator.data

import android.content.SharedPreferences
import com.qoiu.dailytaskmotivator.Read
import com.qoiu.dailytaskmotivator.Save

interface SharedData: Save<Int>,Read<Int> {


    class Gold(private val sharedPreferences: SharedPreferences): SharedData{
        override fun save(data: Int) {
            sharedPreferences.edit().putInt(NAME,data).apply()
        }

        override fun read(): Int =
            sharedPreferences.getInt(NAME,0)

        private companion object{
            const val NAME = "Gold"
        }
    }
}