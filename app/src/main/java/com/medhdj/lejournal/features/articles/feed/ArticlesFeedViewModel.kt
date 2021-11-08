package com.medhdj.lejournal.features.articles.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertHeaderItem
import androidx.paging.insertSeparators
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.medhdj.business.articles.GetArticlesFeedUseCase
import com.medhdj.core.extension.mapAndConvertToLiveDataInBackground
import com.medhdj.core.extension.mapIsFailure
import com.medhdj.core.extension.mapIsLoading
import com.medhdj.core.extension.mapSuccess
import com.medhdj.core.extension.plusAssign
import com.medhdj.core.functionnal.Response
import com.medhdj.lejournal.R
import com.medhdj.lejournal.features.articles.ArticleAgeCategoryEnum
import com.medhdj.lejournal.features.articles.ArticleUIModels
import com.medhdj.lejournal.features.articles.toArticleItemUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticlesFeedViewModel @Inject constructor(
    private val articlesFeedUseCase: GetArticlesFeedUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _articlesFeedData =
        MutableLiveData<Response<Throwable, PagingData<ArticleUIModels>>>()
    val articlesFeedData = _articlesFeedData.mapSuccess()
    val isLoadingData = _articlesFeedData.mapIsLoading()
    val isFailure = _articlesFeedData.mapIsFailure();

    init {
        fetchArticlesFeed()
    }

    private fun fetchArticlesFeed() {
        disposable += articlesFeedUseCase.getArticles(withContent = STATIC_SEARCH_CONTENT)
            .mapAndConvertToLiveDataInBackground(
                dataMapper = {
                    it.map { article ->
                        article.toArticleItemUIModel()
                    }.insertSectionHeaders()
                },
                errorHandler = {
                    Timber.e(it)
                    it
                },
                liveData = _articlesFeedData,
                streamTransformer = {
                    it.cachedIn(viewModelScope)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

private fun PagingData<ArticleUIModels>.insertSectionHeaders() = with(this) {
    insertHeaderItem(item = ArticleUIModels.SectionHeaderUIModel(R.string.section_header_this_week))
        .insertSeparators { before: ArticleUIModels?, after: ArticleUIModels? ->
            if (before is ArticleUIModels.ArticleItemUIModel &&
                after is ArticleUIModels.ArticleItemUIModel &&
                before.articleAgeCategory != after.articleAgeCategory
            ) {
                when {
                    after.articleAgeCategory == ArticleAgeCategoryEnum.LAST_WEEK &&
                            before.articleAgeCategory == ArticleAgeCategoryEnum.THIS_WEEK -> {
                        ArticleUIModels.SectionHeaderUIModel(R.string.section_header_last_week)
                    }
                    else -> {
                        ArticleUIModels.SectionHeaderUIModel(R.string.section_header_older)
                    }
                }
            } else null
        }
}

const val STATIC_SEARCH_CONTENT = "football and covid"
