package com.medhdj.data.articles

import com.medhdj.business.articles.Article
import java.util.*

data class ArticleEntity(
    val id: String,
    val apiUrl: String,
    val webPublicationDate: Date,
    val fields: ArticleFields
)

data class ArticleFields(
    val headline: String,
    val thumbnail: String,
    val main: String? = null,
    val body: String? = null
)

internal fun ArticleEntity.toArticle() = Article(
    id = id,
    headline = fields.headline,
    thumbnailUrl = fields.thumbnail,
    publicationDate = webPublicationDate,
    main = fields.main,
    body = fields.body
)

internal fun List<ArticleEntity>.toArticles() =
    map {
        it.toArticle()
    }
