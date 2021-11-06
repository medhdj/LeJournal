package com.medhdj.lejournal.features.articles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.medhdj.business.articles.GetArticlesFeedUseCase
import com.medhdj.core.extension.mapAndConvertToLiveDataInBackground
import com.medhdj.core.extension.mapSuccess
import com.medhdj.core.functionnal.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticlesFeedViewModel @Inject constructor(
    private val articlesFeedUseCase: GetArticlesFeedUseCase
) : ViewModel() {


    private val _articlesFeedData =
        MutableLiveData<Response<Throwable, PagingData<ArticleUIModel>>>()
    val articlesFeedData = _articlesFeedData.mapSuccess()

    init {
        fetchArticlesFeed()
    }

    private fun fetchArticlesFeed() {
        articlesFeedUseCase.getArticles(withContent = STATIC_SEARCH_CONTENT)
            .cachedIn(viewModelScope)
            .mapAndConvertToLiveDataInBackground(
                successHandler = {
                    it.map { article ->
                        article.toArticleUIModel()
                    }
                },
                errorHandler = {
                    Timber.e(it)
                    it
                },
                liveData = _articlesFeedData
            )
    }
}

const val STATIC_SEARCH_CONTENT = "covid and football"
