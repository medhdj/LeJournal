package com.medhdj.business.articles

import io.reactivex.Observable

interface GetArticlesFeedUseCase {
    fun getArticles(
        withContent: String,
        loadSize: Int,
        page: Int
    ): Observable<List<Article>>
}

class GetArticlesFeedUseCaseImpl(
    private val articlesFeedRepository: ArticlesFeedRepository
) : GetArticlesFeedUseCase {
    override fun getArticles(
        withContent: String,
        loadSize: Int,
        page: Int
    ): Observable<List<Article>> =
        articlesFeedRepository.getArticles(
            withContent = withContent,
            loadSize = loadSize,
            page = page
        )
}
