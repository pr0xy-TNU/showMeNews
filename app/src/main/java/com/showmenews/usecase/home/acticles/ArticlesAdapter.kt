package com.showmenews.usecase.home.acticles

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showmenews.R
import com.showmenews.arch.extensions.inflate
import com.showmenews.arch.extensions.loadFit
import com.showmenews.data.Article
import com.showmenews.usecase.base.BaseRvAdapter
import com.showmenews.utils.formattedDateFromStringToString
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article.view.*


class ArticlesAdapter(
    val onItemClickListener: ((Article) -> Unit)?
) :
    BaseRvAdapter<ArticlesAdapter.ArticleViewHolder, Article>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(parent.inflate(R.layout.item_article, false))


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

    inner class ArticleViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Article, onItemClickListener: ((Article) -> Unit)?) = with(containerView) {

            tvSnippet.text = item.snippet ?: item.headline?.main ?: ""

            item.pubDate?.let {
                try {
                    tvDate.text = formattedDateFromStringToString(it)
                } catch (excp: Exception) {
                    logDebug("Cannot parse data \t $it\t-\t${excp.localizedMessage}")
                }
            }
            val loadableUrl = item.randImgUrl
            ivMultimedia.loadFit(loadableUrl)

            setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }
}