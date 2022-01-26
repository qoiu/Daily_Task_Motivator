package com.qoiu.dailytaskmotivator.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.qoiu.dailytaskmotivator.*
import com.qoiu.dailytaskmotivator.databinding.ActivityMainBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow

class MainActivity : AppCompatActivity(), ViewModelRequest, Save.Gold, DialogShow {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val navigator = Navigator.Base()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = getViewModel(MainViewModel::class.java, this)
        val bottomNav = binding.mainBottomNav
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

    private fun setFragment(fragment: BaseFragment<*, *>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (navigator.onBackPress())
            super.onBackPressed()
    }

    override fun <T : ViewModel> getViewModel(model: Class<T>, owner: ViewModelStoreOwner): T =
        (application as App).viewModel(model, owner)

    override fun save(data: Int) {
        viewModel.save(data)
        updateGold()
    }

    private fun updateGold() {
        binding.goldState.text = viewModel.read().toString()
    }

    override fun show(dialog: DialogFragment) {
        dialog.show(supportFragmentManager, "Dialog")
    }
}