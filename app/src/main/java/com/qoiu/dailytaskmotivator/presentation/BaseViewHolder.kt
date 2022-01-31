package com.qoiu.dailytaskmotivator.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.qoiu.dailytaskmotivator.ViewHolder

abstract class BaseViewHolder<Data>(view: ViewBinding) : RecyclerView.ViewHolder(view.root),
    ViewHolder<Data> {

    abstract override fun bind(data: Data)
}