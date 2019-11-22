package com.showmenews.usecase.home.acticles

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.showmenews.R
import com.showmenews.arch.extensions.logDebug
import com.showmenews.arch.extensions.makeSnackBar
import com.showmenews.data.Article
import com.showmenews.usecase.base.BaseFragment
import com.showmenews.usecase.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticlesFragment : BaseFragment<HomeViewModel>() {
    override val layoutId: Int = R.layout.fragment_articles
    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java
    override var isParentContext = true


    lateinit var articlesAdapter: ArticlesAdapter

    private var currentPage = 0
    private var maxArticlesCount = 80


    override fun onAttached() {
        super.onAttached()
        articlesAdapter = ArticlesAdapter { onItemClick(it) }
    }

    override fun onInitialize() {
        initUI()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        onReset()
        viewModel.getArticles()
    }


    private fun initObservers() {
        viewModel.articlesLive.observe(this, Observer { pair ->
            val (page, articles) = pair
            currentPage = page + 1
            tvNoArticles.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
            if (articles.isEmpty()) {
                makeSnackBar(resources.getString(R.string.no_more_articles_message))
            }
            articlesAdapter.addItems(articles)
        })
    }

    private fun initUI() {
        val layoutManager = LinearLayoutManager(activity)
        rvArticles.layoutManager = layoutManager
        rvArticles.hasFixedSize()
        rvArticles.adapter = articlesAdapter

        val scroll =
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (v.getChildAt(v.childCount - 1) != null) {
                    if (scrollY >= v.getChildAt(0).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {

                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                        if ((articlesAdapter.items.size <= maxArticlesCount) && viewModel.loading.value != true) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount - 5) {
                                onLoadMore(currentPage)
                            }
                        }
                    }
                }
            }
        categoriesNestedScroll.setOnScrollChangeListener(scroll)
    }

    fun onLoadMore(page: Int) {
        logDebug("Need more load data")
        viewModel.getArticles(page)
    }

    private fun onReset() {
        currentPage = 1
        articlesAdapter.clear()
    }


    private fun onItemClick(item: Article) {
        val action =
            ArticlesFragmentDirections.actionDestinationArticlesFragmentToDestinationArticleDetailsFragment(
                item.articleId!!
            )
        navController.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onReset()
    }
}