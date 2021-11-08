package com.medhdj.business.articles

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetArticleDetailsUseCaseImplTest {
    private val articleDetailsRepository: ArticleDetailsRepository = mockk()

    private val tested = GetArticleDetailsUseCaseImpl(articleDetailsRepository)

    @Test
    fun `test fetching data successfully`() {
        // given
        every { tested.getArticle(any()) } returns mockk(relaxed = true)

        // when
        val observer = tested.getArticle("id").test()

        //then
        observer.assertComplete()
    }

    @Test
    fun `test fetching data with errors`() {
        // given
        every {
            tested.getArticle(any())
        } returns Single.error(Exception("some exception"))

        // when
        val observer = tested.getArticle("id").test()

        //then
        observer.assertNotComplete()
        observer.assertError {
            it.message == "some exception"
        }
    }
}
