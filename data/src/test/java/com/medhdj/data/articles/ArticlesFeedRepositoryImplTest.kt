package com.medhdj.data.articles

import com.medhdj.data.common.ResponseData
import com.medhdj.data.common.SearchResponse
import com.medhdj.data.common.TheGuardianApi
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class ArticlesFeedRepositoryImplTest {
    private val theGuardianApi: TheGuardianApi = mockk()
    private val tested = ArticlesFeedRepositoryImpl(theGuardianApi)

    @Test
    fun `test successful fetch`() {
        // given
        every {
            theGuardianApi.searchArticles(
                any(),
                any(),
                any()
            )
        } returns Observable.just(mockk(relaxed = true))

        // when
        val observer = tested.getArticles("abc", 1, 1).test()

        // then
        observer.assertComplete()
        observer.assertNoErrors()
    }

    @Test
    fun `test unsuccessful fetch`() {
        // given
        every {
            theGuardianApi.searchArticles(
                any(),
                any(),
                any()
            )
        } returns Observable.error(Exception("some error"))

        // when
        val observer = tested.getArticles("abc", 1, 1).test()

        // then
        observer.assertNotComplete()
        observer.assertErrorMessage("some error")
    }

    @Test
    fun `test correct mapping`() {
        // given
        every {
            theGuardianApi.searchArticles(
                any(),
                any(),
                any()
            )
        } returns Observable.just(
            SearchResponse(
                data = ResponseData(
                    pageSize = 1,
                    currentPage = 1,
                    totalPages = 10,
                    results = ArticleEntityFixtures.createArticleEntityList(3)
                )
            )
        )

        // when
        val observer = tested.getArticles("abc", loadSize = 10, page = 1).test()

        // then
        observer.assertComplete()
        observer.assertValue {
            it.size == 3
            it[0].id == "id"
            it[0].headline == "head1"
            it[0].thumbnailUrl == "thumb1"
            it[0].body == "body1"
        }
    }
}
