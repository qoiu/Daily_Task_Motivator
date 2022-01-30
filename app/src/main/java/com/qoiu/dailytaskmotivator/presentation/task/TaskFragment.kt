package com.qoiu.dailytaskmotivator.presentation.task

import android.content.DialogInterface
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Save
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.databinding.FragmentTaskBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.Structure
import java.util.*

class TaskFragment : BaseFragment<TaskModel, FragmentTaskBinding>(), Update<Structure>, DialogShow {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTaskBinding.inflate(inflater, container, false)


    override fun layoutResId(): Int = R.layout.fragment_task
    override fun viewModelClass(): Class<TaskModel> = TaskModel::class.java

    private var lastDoneTask = Stack<Structure.Task>()
    private var currentDialogShow: DialogFragment? = null
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
                { newTaskDialog() }) {
                (it as Structure.Task).let { task ->
                    if (!task.reusable) lastDoneTask.push(task)
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

    override fun onBackPress(): Boolean {
        if (currentDialogShow != null) {
            currentDialogShow?.dismiss()
            currentDialogShow = null
            if (lastDoneTask.empty()) return false
        }
        if (!lastDoneTask.empty()) {
            AlertDialog.Builder(requireContext())
                .setTitle(lastDoneTask.peek().title)
                .setMessage(getString(R.string.task_restore_message))
                .setPositiveButton(R.string.ok) { _, _ ->
                    Toast.makeText(
                        requireContext(),
                        "${lastDoneTask.peek().title}: ${getString(R.string.task_restore_success)}",
                        Toast.LENGTH_SHORT
                    ).show()
                    (requireActivity() as Save.Gold).save(lastDoneTask.peek().reward * -1)
                    update(lastDoneTask.pop())
                }
                .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .create().show()
            return false
        }
        return true
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

    private fun newTaskDialog() {
        val dialog = NewTaskDialog(
            {
                update(it)
                currentDialogShow = null
            },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)
        )
        show(dialog)
    }

    private fun editTask(taskDb: Structure) {
        val dialog = NewTaskDialog(
            {
                update(it)
                currentDialogShow = null
            },
            { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() },
            ResourceProvider.String(this.requireContext()),
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories),
            taskDb as Structure.Task
        )
        show(dialog)
    }

    override fun show(dialog: DialogFragment) {
        currentDialogShow = dialog
        dialog.show(requireActivity().supportFragmentManager, "Dialog")
    }

    override fun onPause() {
        currentDialogShow?.dismiss()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        currentDialogShow = null
    }
}