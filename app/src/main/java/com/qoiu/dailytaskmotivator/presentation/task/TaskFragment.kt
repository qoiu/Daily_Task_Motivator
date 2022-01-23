package com.qoiu.dailytaskmotivator.presentation.task

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.FragmentTaskBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.TaskWithCategories

class TaskFragment(
    private val saveGold: Save.Gold,
    private val show: DialogShow
) : BaseFragment<TaskModel, FragmentTaskBinding>(), Update<TaskWithCategories> {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
    }

    override fun layoutResId(): Int = R.layout.fragment_task
    override fun viewModelClass(): Class<TaskModel> = TaskModel::class.java

    private lateinit var progressBar: ProgressBar
    private var categories : List<String> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.taskProgressBar
        progressBar.visibility = View.GONE
        val recyclerView = binding.taskRecycler
        val fab = binding.addFab
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setUpdateListener(recyclerView)
        }
        fab.setOnClickListener { fabAction() }
        val adapter =
            TaskAdapter(emptyList(), show, this, ResourceProvider.String(requireContext()),
                { editTask(it) }) {
                (it as TaskWithCategories.Task).let {task ->
                    saveGold.save((task).reward)
                    viewModel.deleteTask(task)
                }
            }
        recyclerView.adapter = adapter
        viewModel.observe(this, { list ->
            progressBar.visibility = View.GONE
            Log.w("Task", "Updated")
            categories = list.filterIsInstance<TaskWithCategories.Category>().map { it.title }
            adapter.update(list)
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

    override fun update(data: TaskWithCategories) {
        viewModel.saveTask(data)
        progressBar.visibility = View.VISIBLE
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
            { update(it) },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, categories)
        )
        dialog.isCancelable = false
        show.show(dialog)
    }

    private fun editTask(taskDb: TaskWithCategories) {
        val dialog = NewTaskDialog(
            { update(it) },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, categories),
            taskDb as TaskWithCategories.Task
        )
        dialog.isCancelable = false
        show.show(dialog)
    }
}