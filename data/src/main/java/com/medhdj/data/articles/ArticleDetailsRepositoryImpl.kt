package com.medhdj.data.articles

import com.medhdj.business.articles.Article
import com.medhdj.business.articles.ArticleDetailsRepository
import com.medhdj.data.common.TheGuardianApi
import io.reactivex.Single

class ArticleDetailsRepositoryImpl(
    private val theGuardianApi: TheGuardianApi
) : ArticleDetailsRepository {
    override fun getArticle(articleId: String): Single<Article> =
        theGuardianApi.getArticle(
            articleId = articleId,
            extraInformation = mapOf(
                "show-fields" to EXTRA_ARTICLE_FIELDS
            )
        )
            .map {
                it.data.content.toArticle()
            }
}

private const val EXTRA_ARTICLE_FIELDS = "headline,thumbnail,main,body"
