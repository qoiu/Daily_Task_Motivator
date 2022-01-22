package com.qoiu.dailytaskmotivator.presentation.task

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.ViewHolder

abstract class BaseViewHolder<T>(view: View): RecyclerView.ViewHolder(view), ViewHolder<T> {
    abstract override fun bind(data: T)
}