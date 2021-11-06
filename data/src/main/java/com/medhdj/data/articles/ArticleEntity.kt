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
    val thumbnail: String,
    val body: String? = null
)

internal fun ArticleEntity.toArticle() = Article(
    id = id,
    headline = fields.headline,
    thumbnailUrl = fields.thumbnail,
    body = fields.body
)

internal fun List<ArticleEntity>.toArticles() =
    map {
        it.toArticle()
    }
