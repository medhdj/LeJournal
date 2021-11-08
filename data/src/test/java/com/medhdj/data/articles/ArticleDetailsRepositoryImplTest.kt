package com.medhdj.data.articles

import com.medhdj.business.articles.Article
import com.medhdj.data.common.SingleItemData
import com.medhdj.data.common.SingleItemResponse
import com.medhdj.data.common.TheGuardianApi
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class ArticleDetailsRepositoryImplTest {
    private val theGuardianApi: TheGuardianApi = mockk()
    private val tested = ArticleDetailsRepositoryImpl(theGuardianApi)

    @Test
    fun `test successful fetch`() {
        // given
        val response =
            SingleItemResponse(data = SingleItemData(ArticleEntityFixtures.Builder().build()))
        every {
            theGuardianApi.getArticle(any(), any())
        } returns Single.just(response)

        // when
        val observer = tested.getArticle("id").test()

        // then
        observer.assertComplete()
    }

    @Test
    fun `test unsuccessful fetch`() {
        // given
        every {
            theGuardianApi.getArticle(any(), any())
        } returns Single.error(Exception("some exception"))

        // when
        val observer = tested.getArticle("id").test()

        // then
        observer.assertErrorMessage("some exception")
    }

    @Test
    fun `test correct mapping to business model`() {
        // given
        val articleEntity = ArticleEntityFixtures.Builder().build()
        val response =
            SingleItemResponse(SingleItemData(articleEntity))
        every {
            theGuardianApi.getArticle(any(), any())
        } returns Single.just(response)

        // when
        val observer = tested.getArticle("id").test()

        // then
        observer.assertValue {
            it is Article && it.id == articleEntity.id
        }
    }
}
