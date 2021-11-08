package com.medhdj.data.articles

import java.util.Date

object ArticleEntityFixtures {

    fun createArticleEntityList(size: Int) = MutableList(size) { Builder().build() }

    class Builder(
        val id: String = "id",
        val apiUrl: String = "apiurl",
        val webPublicationDate: Date = Date(),
        val fields: ArticleFields = ArticleFields(
            headline = "head1",
            thumbnail = "thumb1",
            body = "body1"
        )
    ) {
        fun build() = ArticleEntity(
            id = id,
            apiUrl = apiUrl,
            webPublicationDate = webPublicationDate,
            fields = fields
        )
    }
}
