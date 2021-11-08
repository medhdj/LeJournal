package com.medhdj.lejournal.features.articles.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.medhdj.business.articles.GetArticleDetailsUseCase
import com.medhdj.core.extension.map
import com.medhdj.core.extension.mapAndConvertToLiveDataInBackground
import com.medhdj.core.extension.mapSuccess
import com.medhdj.core.extension.plusAssign
import com.medhdj.core.functionnal.Response
import com.medhdj.lejournal.features.articles.ArticleUIModels
import com.medhdj.lejournal.features.articles.toArticleUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val articleDetailsUseCase: GetArticleDetailsUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        const val ARTICLE_ID = "articleId"
    }

    private val disposable = CompositeDisposable()

    private val _articleDetailsData =
        MutableLiveData<Response<Throwable, ArticleUIModels.ArticleUIModel>>()
    val articleDetailsData = _articleDetailsData.mapSuccess().map {
        it
    }

    private val articleId by lazy {
        state.get<String>(ARTICLE_ID)!!
    }

    init {
        fetchArticleDetails()
    }

    private fun fetchArticleDetails() {
        disposable += articleDetailsUseCase.getArticle(articleId = articleId)
            .mapAndConvertToLiveDataInBackground(
                successHandler = {
                    it.toArticleUIModel()
                },
                errorHandler = {
                    Timber.e(it)
                    it
                },
                liveData = _articleDetailsData
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
