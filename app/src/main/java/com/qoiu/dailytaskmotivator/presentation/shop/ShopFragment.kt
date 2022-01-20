package com.qoiu.dailytaskmotivator.presentation.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.databinding.FragmentShopBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment

class ShopFragment : BaseFragment<ShopModel,FragmentShopBinding>() {

    override fun viewModelClass(): Class<ShopModel> = ShopModel::class.java
    override fun layoutResId(): Int = R.layout.fragment_shop
    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentShopBinding.inflate(inflater,container,false)
    }
}