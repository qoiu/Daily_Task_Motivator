package com.qoiu.dailytaskmotivator.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.qoiu.dailytaskmotivator.*
import com.qoiu.dailytaskmotivator.databinding.ActivityMainBinding
import com.qoiu.dailytaskmotivator.presentation.BaseFragment
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), Save.Gold, DialogShow {

    private val model: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val navigator = Navigator.Base()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNav = binding.mainBottomNav
        bottomNav.setOnItemSelectedListener {
            setFragment(navigator.navigate(it.itemId))
            return@setOnItemSelectedListener true
        }
        bottomNav.selectedItemId = R.id.nav_tasks
        model.observe(this, {
            navigator.updateFragment()
        })
        updateGold()
    }

    private fun setFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (navigator.onBackPress())
            super.onBackPressed()
    }

    override fun save(data: Int) {
        model.save(data)
        updateGold()
    }

    private fun updateGold() {
        binding.goldState.text = model.read().toString()
    }

    override fun show(dialog: DialogFragment) {
        dialog.show(supportFragmentManager, "Dialog")
    }
}