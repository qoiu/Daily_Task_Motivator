package com.qoiu.dailytaskmotivator.presentation.task

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.Communication
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow

class TaskFragment(
    private val communication: Communication<List<TaskDb>>,
    private val saveGold: Save.Gold,
    private val show: DialogShow
) : BaseFragment<TaskModel>(), Update<TaskDb> {

    override fun layoutResId(): Int = R.layout.fragment_task
    override fun viewModelClass(): Class<TaskModel> = TaskModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recycler)
        val adapter = TaskAdapter(emptyList(), show, this) {
            saveGold.save(it.reward)
            viewModel.deleteTask(it)
        }
        recyclerView.adapter = adapter
        viewModel.observe(this, {
            adapter.update(it)
        })
        communication.observe(this, { adapter.update(it) })
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateData()
    }

    override fun update(data: TaskDb) {
        viewModel.update(data)
    }

    override fun update() {
        viewModel.updateData()
    }
}