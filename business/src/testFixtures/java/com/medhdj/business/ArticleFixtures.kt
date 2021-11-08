package com.medhdj.business

import com.medhdj.business.articles.Article
import java.util.Date

object ArticleFixtures {

    fun createArticlesList(size: Int) = MutableList(size) { Builder().build() }

    data class Builder(
        val id: String = "id",
        val headline: String = "headline",
        val thumbnailUrl: String = "url",
        val publicationDate: Date = Date(),
        val body: String? = null,
        val main: String? = null
    ) {
        fun build() = Article(
            id = id,
            headline = headline,
            thumbnailUrl = thumbnailUrl,
            publicationDate = publicationDate,
            body = body,
            main = main
        )
    }
}
