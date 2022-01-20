package com.qoiu.dailytaskmotivator.presentation.task

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow

class TaskFragment(
    private val saveGold: Save.Gold,
    private val show: DialogShow
) : BaseFragment<TaskModel>(), Update<TaskDb> {

    override fun layoutResId(): Int = R.layout.fragment_task
    override fun viewModelClass(): Class<TaskModel> = TaskModel::class.java

    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.task_progressBar)
        progressBar.visibility = View.GONE
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recycler)
        val fab = view.findViewById<FloatingActionButton>(R.id.add_fab)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setUpdateListener(recyclerView)
        }
        fab.setOnClickListener { fabAction() }
        val adapter =
            TaskAdapter(emptyList(), show, this, ResourceProvider.String(requireContext()),
                { editTask(it) }) {
                saveGold.save(it.reward)
                viewModel.deleteTask(it)
            }
        recyclerView.adapter = adapter
        viewModel.observe(this, {
            progressBar.visibility = View.GONE
            Log.w("Task", "Updated")
            adapter.update(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpdateListener(recyclerView: RecyclerView) {
        recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            val view: View = recyclerView.getChildAt(0)
            val diff: Int = view.top - (recyclerView.scrollY)
            if (diff > 0) {
                update()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateData()
    }

    override fun update(data: TaskDb) {
        viewModel.update(data)
    }

    override fun update() {
        if (progressBar.visibility == View.GONE) {
            progressBar.visibility = View.VISIBLE
            Log.w("Task", "Update")
            viewModel.updateData()
        }
    }

    private fun fabAction() {
        val dialog = NewTaskDialog(
            { viewModel.addTask(it) },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext())
        )
        dialog.isCancelable = false
        show.show(dialog)
    }

    private fun editTask(taskDb: TaskDb) {
        val dialog = NewTaskDialog(
            { update(it) },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            taskDb
        )
        dialog.isCancelable = false
        show.show(dialog)
    }
}