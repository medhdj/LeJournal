package com.medhdj.business.articles

import java.util.Date

data class Article(
    val id: String,
    val headline: String,
    val thumbnailUrl: String,
    val publicationDate: Date,
    val main: String? = null,
    val body: String? = null

)
