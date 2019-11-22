package com.showmenews.usecase.home

import androidx.lifecycle.MutableLiveData
import com.showmenews.arch.extensions.logDebug
import com.showmenews.data.Article
import com.showmenews.usecase.base.StatefulViewModel
import com.showmenews.utils.DateFormats
import com.showmenews.utils.SingleLiveData
import com.showmenews.utils.formattedDateFromDateToString
import com.showmenews.utils.parseThrowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.*

class HomeViewModel : StatefulViewModel() {
    var articlesLive = MutableLiveData<Pair<Int, List<Article>>>()
    var singleArticleLive = SingleLiveData<Article>()

    var categoriesList = mutableListOf<String>()
    var selectedCategories = mutableSetOf<String>()

    var articlesSet = mutableSetOf<Article>()


    fun saveCategories(data: Collection<String>) {
        categoriesList.addAll(data)
    }


    fun getArticles(page: Int = 1) {
        if (!startOperation("load_articles")) return
        val query = prepareQuery()
        _loading.value = true
        logDebug("Start to get \t $page page.")
        val (beginDate, endDate) = prepareDate()

        createActionAndRun {
            remoteRepository.apiService.getNews(
                hashMapOf(
                    "fq" to query,
                    "page" to page,
                    "begin_date" to beginDate,
                    "end_date" to endDate
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _loading.value = true }
                .doAfterTerminate { _loading.value = false }
                .subscribe({ response ->
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        val articles = responseBody?.response?.docs
                        articles?.let {
                            it.sortedBy { it.pubDate }
                            articlesSet.addAll(articles.toHashSet())
                            articlesLive.postValue(Pair(page, articles))
                        }
                    } else {
                        logDebug("Error while request \t ${response.errorBody()?.string()}")
                        _error.postValue(parseThrowable(HttpException(response)))
                    }

                }, { throwable ->
                    logDebug("Throwable happened ${throwable.localizedMessage}")
                    _error.postValue(parseThrowable(throwable))
                }).bind(this)
        }
    }

    fun getArticleById(articleId: String) {
        val article = articlesSet.firstOrNull { it.articleId.equals(articleId) }
        _loading.value = true
        article?.let {
            singleArticleLive.postValue(article)
            _loading.postValue(false)
        }
    }

    /**
     * Get last 6 moths news
     */
    fun prepareDate(): Pair<String, String> {
        val calendar = Calendar.getInstance()
        val endDate = formattedDateFromDateToString(calendar.time, DateFormats.yyyyMMdd)

        calendar.add(Calendar.MONTH, -6)
        val beginDate = formattedDateFromDateToString(calendar.time, DateFormats.yyyyMMdd)

        return Pair(beginDate, endDate)
    }

    fun prepareQuery(): String =
        selectedCategories.joinToString(" ", "news_desk:(", ")") { "\"$it\"" }
}

