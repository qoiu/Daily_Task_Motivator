package com.qoiu.dailytaskmotivator

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes

interface ResourceProvider {
    interface SharedProvider {
        fun provideSharedPreference(): SharedPreferences
    }

    interface StringProvider {
        fun string(@StringRes id: Int): kotlin.String
    }

    class Shared(private val context: Context) : SharedProvider {
        override fun provideSharedPreference(): SharedPreferences =
            context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

        private companion object {
            const val NAME = "User"
        }
    }

    class String(private val context: Context) : StringProvider {
        override fun string(id: Int): kotlin.String = context.getString(id)

    }
}