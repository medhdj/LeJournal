package com.medhdj.business.articles

import io.reactivex.Single

interface ArticleDetailsRepository {
    fun getArticle(articleId: String): Single<Article>
}
