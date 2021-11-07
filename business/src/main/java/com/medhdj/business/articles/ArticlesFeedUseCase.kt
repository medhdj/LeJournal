package com.medhdj.business.articles

import androidx.paging.PagingData
import io.reactivex.Flowable

interface GetArticlesFeedUseCase {
    fun getArticles(withContent: String): Flowable<PagingData<Article>>
}

class GetArticlesFeedUseCaseImpl(
    private val articlesFeedRepository: ArticlesFeedRepository
) : GetArticlesFeedUseCase {
    override fun getArticles(withContent: String): Flowable<PagingData<Article>> =
        articlesFeedRepository.getArticles(withContent = withContent)
}

const val DEFAULT_PAGE_SIZE = 50
