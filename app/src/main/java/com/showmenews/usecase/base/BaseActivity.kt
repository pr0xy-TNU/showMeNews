package com.showmenews.usecase.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.showmenews.R

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: Class<VM>
    protected lateinit var viewModel: VM
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
        navController = findNavController(R.id.navHostFragment)
        onInitialize()
    }

    open fun onInitialize() {

    }
}