package com.medhdj.business.articles

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.Test

class GetArticlesFeedUseCaseImplTest {
    private val articlesFeedRepository: ArticlesFeedRepository = mockk()

    private val tested = GetArticlesFeedUseCaseImpl(articlesFeedRepository)

    @Test
    fun `test fetching data successfully`() {
        // given
        every { tested.getArticles(any()) } returns mockk(relaxed = true)

        // when
        val observer = tested.getArticles("test").test()

        //then
        observer.assertComplete()
    }

    @Test
    fun `test fetching data with errors`() {
        // given
        every {
            tested.getArticles(any())
        } returns Flowable.error(Exception("some exception"))

        // when
        val observer = tested.getArticles("test").test()

        //then
        observer.assertNotComplete()
        observer.assertError {
            it.message == "some exception"
        }
    }
}
