package com.qoiu.dailytaskmotivator.presentation.shop

import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.presentation.BaseFragment

class ShopFragment : BaseFragment<ShopModel>() {

    override fun viewModelClass(): Class<ShopModel> = ShopModel::class.java
    override fun layoutResId(): Int = R.layout.fragment_shop
}