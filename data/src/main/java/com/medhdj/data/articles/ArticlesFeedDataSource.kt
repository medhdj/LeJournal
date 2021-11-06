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

    private var contentToSearch: String? = null

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Article>> {
        val position = params.key ?: DEFAULT_PAGE_START_INDEX

        return theGuardianApi
            .searchArticles(
                withContent = contentToSearch ?: "",
                pageSize = params.loadSize,
                page = position,
                extraInformation = mapOf(
                    "show-fields" to EXTRA_ARTICLE_FIELDS
                )
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

    fun updateContentToSearch(newContent: String): ArticlesFeedDataSource {
        contentToSearch = newContent
        return this
    }
}

const val DEFAULT_PAGE_START_INDEX = 1
const val EXTRA_ARTICLE_FIELDS = "headline,thumbnail"
