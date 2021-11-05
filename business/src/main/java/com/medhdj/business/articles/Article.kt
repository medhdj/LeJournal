package com.medhdj.business.articles

data class Article(
    val id: String,
    val headline: String,
    val thumbnailUrl: String,
    val body: String? = null,
    val main: String? = null
)
