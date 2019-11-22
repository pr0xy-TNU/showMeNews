package com.showmenews.usecase.home

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.ui.NavigationUI
import com.showmenews.R
import com.showmenews.arch.extensions.logging
import com.showmenews.arch.extensions.makeSnackBar
import com.showmenews.usecase.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<HomeViewModel>() {
    override val layoutId: Int = R.layout.activity_home
    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java


    override fun onInitialize() {
        setupToolbar()
        initObservers()
    }

    fun initObservers() {
        viewModel.loading.observe(this, Observer {
            layoutProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer { error ->
            makeSnackBar(error.message!!)
        })
    }


    fun setupToolbar() {
        setSupportActionBar(toolbar)
        NavigationUI.setupWithNavController(toolbar, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            logging("Current destination id \t${destination.label}")
        }
    }
}