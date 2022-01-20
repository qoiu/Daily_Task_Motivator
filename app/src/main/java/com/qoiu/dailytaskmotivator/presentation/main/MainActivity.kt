package com.qoiu.dailytaskmotivator.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qoiu.dailytaskmotivator.*
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow

class MainActivity : AppCompatActivity(), ViewModelRequest, Save.Gold, DialogShow {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = getViewModel(MainViewModel::class.java, this)
        val navigator = Navigator.Base(this, this)
        val bottomNav = findViewById<BottomNavigationView>(R.id.main_bottom_nav)
        bottomNav.setOnItemSelectedListener {
            setFragment(navigator.navigate(it.itemId))
            return@setOnItemSelectedListener true
        }
        bottomNav.selectedItemId = R.id.nav_tasks
        viewModel.observe(this, {
            navigator.updateFragment()
        })
        updateGold()
    }

    private fun setFragment(fragment: BaseFragment<*>) {
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
        this.supportActionBar?.title = "Gold: ${viewModel.read()}"
    }

    override fun show(dialog: DialogFragment) {
        dialog.show(supportFragmentManager, "Dialog")
    }
}