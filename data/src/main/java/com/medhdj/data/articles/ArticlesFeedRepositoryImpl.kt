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
    override fun getArticles(withContent: String): Flowable<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = 5,
                initialLoadSize = DEFAULT_PAGE_SIZE * 2,
                maxSize = 60
            ),
            pagingSourceFactory = {
                articlesFeedDataSource.updateArticleFeedQuery(
                    ArticlesFeedDataSource.ArticleFeedQuery(
                        contentToSearch = withContent,
                        order = DEFAULT_RESULT_ORDER,
                        extraInformation = mapOf(
                            "show-fields" to EXTRA_ARTICLE_FIELDS
                        )
                    )
                )
                articlesFeedDataSource
            }
        ).flowable
}

private const val DEFAULT_PAGE_SIZE = 50
private const val DEFAULT_RESULT_ORDER = "newest"
