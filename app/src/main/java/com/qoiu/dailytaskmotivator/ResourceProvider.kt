package com.qoiu.dailytaskmotivator

import android.content.Context
import android.content.SharedPreferences

interface ResourceProvider {
    fun provideSharedPreference(): SharedPreferences

    class Base(private val context: Context): ResourceProvider{
        override fun provideSharedPreference(): SharedPreferences =
            context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        private companion object{
            const val NAME = "User"
        }
    }
}