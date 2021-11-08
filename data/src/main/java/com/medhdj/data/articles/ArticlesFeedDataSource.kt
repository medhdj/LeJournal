package com.medhdj.data.articles

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.medhdj.business.articles.Article
import com.medhdj.core.extension.runAndObserveInBackground
import com.medhdj.data.common.TheGuardianApi
import io.reactivex.Single

class ArticlesFeedDataSource(
    private val theGuardianApi: TheGuardianApi
) : RxPagingSource<Int, Article>() {

    private lateinit var articleFeedQuery: ArticleFeedQuery

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Article>> {
        val position = params.key ?: DEFAULT_PAGE_START_INDEX

        return theGuardianApi
            .searchArticles(
                withContent = articleFeedQuery.contentToSearch,
                pageSize = params.loadSize,
                page = position,
                order = articleFeedQuery.order,
                extraInformation = articleFeedQuery.extraInformation
            )
            .runAndObserveInBackground()
            .map<LoadResult<Int, Article>> {
                LoadResult.Page(
                    data = it.data.results.toArticles(),
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == it.data.totalPages) null else position + 1
                )
            }
            .onErrorReturn { e ->
                LoadResult.Error(e)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null

    data class ArticleFeedQuery(
        val contentToSearch: String,
        val order: String,
        val extraInformation: Map<String, String> = emptyMap()
    )

    fun updateArticleFeedQuery(newContent: ArticleFeedQuery) {
        articleFeedQuery = newContent
    }
}

const val DEFAULT_PAGE_START_INDEX = 1
