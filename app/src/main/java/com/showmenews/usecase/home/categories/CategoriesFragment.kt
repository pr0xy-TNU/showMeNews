package com.showmenews.usecase.home.categories

import androidx.recyclerview.widget.LinearLayoutManager
import com.showmenews.R
import com.showmenews.arch.extensions.makeSnackBar
import com.showmenews.usecase.base.BaseFragment
import com.showmenews.usecase.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_categories.*


class CategoriesFragment : BaseFragment<HomeViewModel>() {
    override val layoutId: Int = R.layout.fragment_categories
    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java
    override var isParentContext = true

    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onAttached() {
        setMenuVisibility(true)
        categoriesAdapter = CategoriesAdapter()
        val data = resources.getStringArray(R.array.article_categories).toList()
        viewModel.saveCategories(data)
        categoriesAdapter.addItems(data)
    }

    override fun onInitialize() {
        initUI()

    }

    fun initUI() {
        rvCategories.layoutManager = LinearLayoutManager(activity)
        rvCategories.adapter = categoriesAdapter
        rvCategories.hasFixedSize()

        val action =
            CategoriesFragmentDirections.actionDestinationCategoriesFragmentToDestinationArticlesFragment()
        btnSubmit.setOnClickListener {
            if (categoriesAdapter.selectedItemSet.isEmpty()) {
                makeSnackBar("Please select the category")
            } else {
                viewModel.selectedCategories = categoriesAdapter.selectedItemSet
                navController.navigate(action)
            }
        }
    }



}