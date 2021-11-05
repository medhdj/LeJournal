package com.medhdj.business.articles

import com.medhdj.business.ArticleFixtures
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class GetArticlesFeedUseCaseImplTest {
    private val articlesFeedRepository: ArticlesFeedRepository = mockk()

    private val tested = GetArticlesFeedUseCaseImpl(articlesFeedRepository)

    @Test
    fun `test fetching data successfully`() {
        // given
        val articles = ArticleFixtures.createArticlesList(size = 2)
        every { tested.getArticles(any(), any(), any()) } returns Observable.just(articles)

        // when
        val observer = tested.getArticles("test", 10, 2).test()

        //then
        observer.assertComplete()
        observer.assertValue {
            it.size == articles.size
        }
    }

    @Test
    fun `test fetching data with errors`() {
        // given
        every {
            tested.getArticles(
                any(),
                any(),
                any()
            )
        } returns Observable.error(Exception("some exception"))

        // when
        val observer = tested.getArticles("test", 10, 2).test()

        //then
        observer.assertNotComplete()
        observer.assertError {
            it.message == "some exception"
        }
    }
}
