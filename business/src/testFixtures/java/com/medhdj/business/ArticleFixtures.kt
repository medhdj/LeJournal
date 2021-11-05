package com.medhdj.business

import com.medhdj.business.articles.Article

object ArticleFixtures {

    fun createArticlesList(size: Int) = MutableList(size) { Builder().build() }

    data class Builder(
        val id: String = "id",
        val headline: String = "headline",
        val thumbnailUrl: String = "url",
        val body: String? = null,
        val main: String? = null
    ) {
        fun build() = Article(
            id = id,
            headline = headline,
            thumbnailUrl = thumbnailUrl,
            body = body,
            main = main
        )
    }
}
