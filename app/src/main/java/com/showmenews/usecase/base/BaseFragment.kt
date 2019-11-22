package com.showmenews.usecase.base

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.showmenews.arch.extensions.closeKeyboard

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: Class<VM>
    protected lateinit var viewModel: VM
    protected abstract var isParentContext: Boolean

    lateinit var navController: NavController

    private var navId: Int? = null

    protected var snackBar: Snackbar? = null


    protected fun NavController.navigateChecked(action: NavDirections) {
        if (navId == this.currentDestination?.id ?: return) {
            navigate(action)
        }
    }

    protected fun NavController.navigateChecked(@IdRes id: Int) {
        if (navId == this.currentDestination?.id ?: return)
            navigate(id)
    }


    open fun onInitialize() {

    }

    open fun onAttached() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (isParentContext) {
            viewModel = ViewModelProviders.of(activity!!).get(viewModelClass)
        } else {
            viewModel = ViewModelProviders.of(this).get(viewModelClass)
        }
        onAttached()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        onInitialize()
    }

    override fun onPause() {
        super.onPause()
        closeKeyboard()
    }

    override fun onResume() {
        super.onResume()
        closeKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        snackBar?.dismiss()
    }
}