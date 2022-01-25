package com.qoiu.dailytaskmotivator.presentation.task

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.FragmentTaskBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.Structure

class TaskFragment : BaseFragment<TaskModel, FragmentTaskBinding>(), Update<Structure>, DialogShow {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
    }

    override fun layoutResId(): Int = R.layout.fragment_task
    override fun viewModelClass(): Class<TaskModel> = TaskModel::class.java

    private var currentDialogShow : DialogFragment? = null
    private lateinit var progressBar: ProgressBar
    private var categories: List<String> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.taskProgressBar
        progressBar.visibility = View.GONE
        val recyclerView = binding.taskRecycler
        val adapter =
            TaskAdapter(emptyList(), this, this, ResourceProvider.String(requireContext()),
                { editTask(it) },
                { fabAction() }) {
                (it as Structure.Task).let { task ->
                    (requireActivity() as Save.Gold).save(task.reward)
                    viewModel.deleteTask(task)
                }
            }
        recyclerView.adapter = adapter
        viewModel.observe(this, { list ->
            progressBar.visibility = View.GONE
            Log.w("Task", "Updated")
            categories = list.filterIsInstance<Structure.Category>().map { it.title }
            adapter.update(list)
        })
    }


    override fun onStart() {
        super.onStart()
        update()
    }

    override fun update(data: Structure) {
        viewModel.saveTask(data)
        progressBar.visibility = View.VISIBLE
    }

    override fun update() {
        if (progressBar.visibility == View.GONE) {
            progressBar.visibility = View.VISIBLE
            Log.w("Task", "Update")
            viewModel.updateData(requireActivity().resources.configuration.orientation == ORIENTATION_PORTRAIT)
        }
    }

    private fun fabAction() {
        val dialog = NewTaskDialog(
            { update(it) },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)
        )
        dialog.isCancelable = false
        show(dialog)
    }

    private fun editTask(taskDb: Structure) {
        val dialog = NewTaskDialog(
            { update(it) },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories),
            taskDb as Structure.Task
        )
        dialog.isCancelable = false
        show(dialog)
    }

    override fun show(dialog: DialogFragment) {
        currentDialogShow = dialog
        Log.w("Dialog",currentDialogShow?.javaClass?.name?:"empty")
        dialog.show(requireActivity().supportFragmentManager, "Dialog")
    }

    override fun onPause() {
        Log.w("Dialog",currentDialogShow?.javaClass?.name?:"empty")
        currentDialogShow?.dismiss()
        super.onPause()
    }
}