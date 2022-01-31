package com.qoiu.dailytaskmotivator.presentation.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.databinding.FragmentShopBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopFragment : BaseFragment<FragmentShopBinding>() {

    private val model: ShopModel by viewModel()
    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentShopBinding.inflate(inflater,container,false)

    override fun init(binding: FragmentShopBinding) {
        TODO("Not yet implemented")
    }
}