package com.medhdj.data.articles

import com.medhdj.business.articles.Article
import com.medhdj.business.articles.ArticlesFeedRepository
import com.medhdj.data.common.GuardianApi
import com.medhdj.data.common.SearchableEndpoint
import io.reactivex.Observable

class ArticlesFeedRepositoryImpl(
    private val guardianApi: GuardianApi
) : ArticlesFeedRepository {
    override fun getArticles(
        withContent: String,
        loadSize: Int,
        page: Int
    ): Observable<List<Article>> =
        guardianApi.search<ArticleEntity>(
            endpoint = SearchableEndpoint.CONTENT_ENDPOINT,
            withContent = withContent,
            pageSize = loadSize,
            extraInformation = mapOf(
                "show-fields" to "headline,thumbnail"
            )
        ).map {
            it.toArticles()
        }
}
