package com.medhdj.data.articles

object ArticleEntityFixtures {

    fun createArticleEntityList(size: Int) = MutableList(size) { Builder().build() }

    data class Builder(
        val id: String = "id",
        val apiUrl: String = "apiurl",
        val fields: ArticleFields = ArticleFields(
            headline = "head1",
            thumbnail = "thumb1",
            body = "body1"
        )
    ) {
        fun build() = ArticleEntity(
            id = id,
            apiUrl = apiUrl,
            fields = fields
        )
    }
}
