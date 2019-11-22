package com.showmenews.usecase.home.deteils

import androidx.lifecycle.Observer
import com.showmenews.R
import com.showmenews.arch.extensions.loadFit
import com.showmenews.arch.extensions.logDebug
import com.showmenews.usecase.base.BaseFragment
import com.showmenews.usecase.home.HomeViewModel
import com.showmenews.utils.formattedDateFromStringToString
import kotlinx.android.synthetic.main.fragment_article_details.*

class ArticleDetailsFragment : BaseFragment<HomeViewModel>() {
    override val layoutId: Int = R.layout.fragment_article_details
    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java
    override var isParentContext = true


    override fun onAttached() {
        val articleId = ArticleDetailsFragmentArgs.fromBundle(arguments!!).articleId
        viewModel.getArticleById(articleId)
    }

    override fun onInitialize() {
        initObservers()
    }

    fun initObservers() {
        viewModel.singleArticleLive.observe(this, Observer {

            tvArticleTitle.text = it.headline?.main ?: ""
            tvContent.text = it.leadParagraph ?: "No content"
            ivMultimedia.loadFit(it.randImgUrl)
            it.pubDate?.let { strDate ->
                try {
                    tvPabDate.text = formattedDateFromStringToString(strDate)
                } catch (excp: Exception) {
                    logDebug("Cannot parse data \t $strDate")
                }
            }
        })
    }
}