package com.medhdj.business.articles

import io.reactivex.Single

interface GetArticleDetailsUseCase {
    fun getArticle(articleId: String): Single<Article>
}

class GetArticleDetailsUseCaseImpl(
    private val articleDetailsRepository: ArticleDetailsRepository
) : GetArticleDetailsUseCase {
    override fun getArticle(articleId: String): Single<Article> =
        articleDetailsRepository.getArticle(articleId = articleId)
}
