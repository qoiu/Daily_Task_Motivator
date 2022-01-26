package com.qoiu.dailytaskmotivator.presentation.main

import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.shop.ShopFragment
import com.qoiu.dailytaskmotivator.presentation.task.TaskFragment

interface Navigator {
    fun navigate(id: Int): BaseFragment<*,*>
    fun updateFragment()
    fun onBackPress() : Boolean

    class Base: Navigator {

        private lateinit var fragment: BaseFragment<*,*>
        override fun navigate(id: Int): BaseFragment<*,*> {
            fragment = when (id) {
                R.id.nav_tasks -> TaskFragment()
                R.id.nav_shop -> ShopFragment()
                else -> TaskFragment()
            }
            return fragment
        }

        override fun updateFragment() = fragment.update()
        override fun onBackPress(): Boolean = fragment.onBackPress()
    }
}