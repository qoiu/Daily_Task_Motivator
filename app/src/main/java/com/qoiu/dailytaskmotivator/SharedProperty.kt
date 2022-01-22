package com.qoiu.dailytaskmotivator

sealed class SharedProperty {

    abstract class Base<T>: SharedProperty(){
        protected abstract val key: kotlin.String
        protected abstract val value: T
        fun value() = value
        fun key() = key
    }
    class String(override val key: kotlin.String, override val value: kotlin.String) : SharedProperty.Base<kotlin.String>()
    class Int(override val key: kotlin.String, override val value: kotlin.Int): SharedProperty.Base<kotlin.Int>()

}