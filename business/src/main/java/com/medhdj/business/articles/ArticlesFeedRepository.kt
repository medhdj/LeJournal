package com.medhdj.business.articles

import androidx.paging.PagingData
import io.reactivex.Flowable

interface ArticlesFeedRepository {
    fun getArticles(withContent: String): Flowable<PagingData<Article>>
}
