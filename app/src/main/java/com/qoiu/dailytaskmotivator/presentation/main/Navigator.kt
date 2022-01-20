package com.qoiu.dailytaskmotivator.presentation.main

import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.shop.ShopFragment
import com.qoiu.dailytaskmotivator.presentation.task.TaskFragment

interface Navigator {
    fun navigate(id: Int): BaseFragment<*,*>
    fun updateFragment()

    class Base(
        private val saveGold: Save.Gold,
        private val dialogShow: DialogShow
    ): Navigator {

        private lateinit var fragment: BaseFragment<*,*>
        override fun navigate(id: Int): BaseFragment<*,*> {
            fragment = when (id) {
                R.id.nav_tasks -> TaskFragment(saveGold, dialogShow)
                R.id.nav_shop -> ShopFragment()
                else -> TaskFragment(saveGold, dialogShow)
            }
            return fragment
        }

        override fun updateFragment() = fragment.update()
    }
}