package com.medhdj.data.articles

import com.medhdj.business.articles.Article
import com.medhdj.data.common.SearchResponse

data class ArticleEntity(
    val id: String,
    val apiUrl: String,
    val fields: ArticleFields
)

data class ArticleFields(
    val headline: String,
    val thumbnailUrl: String,
    val body: String? = null
)

internal fun ArticleEntity.toArticle() = Article(
    id = id,
    headline = fields.headline,
    thumbnailUrl = fields.thumbnailUrl,
    body = fields.body
)

internal fun SearchResponse<ArticleEntity>.toArticles() =
    results.map {
        it.toArticle()
    }
