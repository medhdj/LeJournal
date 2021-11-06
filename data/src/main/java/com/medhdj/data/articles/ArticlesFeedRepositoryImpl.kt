package com.medhdj.data.articles

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.medhdj.business.articles.Article
import com.medhdj.business.articles.ArticlesFeedRepository
import io.reactivex.Flowable

class ArticlesFeedRepositoryImpl(
    private val articlesFeedDataSource: ArticlesFeedDataSource
) : ArticlesFeedRepository {
    override fun getArticles(
        withContent: String,
        pageSize: Int
    ): Flowable<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = 5,
                initialLoadSize = DEFAULT_PAGE_SIZE * 2,
                maxSize = 60
            ),
            pagingSourceFactory = {
                articlesFeedDataSource.updateContentToSearch(newContent = withContent)
            }
        ).flowable
}

const val DEFAULT_PAGE_SIZE = 50
