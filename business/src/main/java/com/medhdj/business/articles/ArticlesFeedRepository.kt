package com.medhdj.business.articles

import io.reactivex.Observable

interface ArticlesFeedRepository {
    fun getArticles(
        withContent: String,
        loadSize: Int,
        page: Int
    ): Observable<List<Article>>
}
