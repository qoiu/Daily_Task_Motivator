package com.qoiu.dailytaskmotivator.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.qoiu.dailytaskmotivator.*
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ViewModelRequest, Save.Gold, DialogShow {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = getViewModel(MainViewModel::class.java, this)
        val communication = object : Communication.Base<List<TaskDb>>() {}
        val fragment = TaskFragment(communication, this, this)
        findViewById<FloatingActionButton>(R.id.add_fab).setOnClickListener {
            val dialog = NewTaskDialog({
                viewModel.addTask(it)
                viewModel.getData()
            },
                {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            dialog.isCancelable = false
            show(dialog)
        }
        viewModel.observe(this, {
            Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
            fragment.update()
            communication.provide(it)
        })
        updateGold()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun <T : ViewModel> getViewModel(model: Class<T>, owner: ViewModelStoreOwner): T =
        (application as App).viewModel(model, owner)

    override fun save(data: Int) {
        viewModel.save(data)
        updateGold()
    }

    private fun updateGold() {
        Toast.makeText(this, "Gold: ${viewModel.read()}", Toast.LENGTH_SHORT).show()
        this.supportActionBar?.title = "Gold: ${viewModel.read()}"
    }

    override fun show(dialog: DialogFragment) {
        dialog.show(supportFragmentManager, "Dialog")
    }
}