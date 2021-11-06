package com.medhdj.lejournal.features.articles

import com.medhdj.business.articles.Article

data class ArticleUIModel(
    val id: String,
    val headline: String
)

internal fun Article.toArticleUIModel() =
    ArticleUIModel(
        id = id,
        headline = headline
    )

internal fun List<Article>.toArticleUIModels() =
    map {
        it.toArticleUIModel()
    }
