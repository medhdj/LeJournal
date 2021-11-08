package com.medhdj.lejournal.features.articles.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.medhdj.business.ArticleFixtures
import com.medhdj.business.articles.GetArticleDetailsUseCase
import com.medhdj.lejournal.common.RxTestUtils
import com.medhdj.lejournal.common.test
import com.medhdj.lejournal.features.articles.details.ArticleDetailsViewModel.Companion.ARTICLE_ID
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class ArticleDetailsViewModelTest {
    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxTestUtils().getTestingRule()

    private val articleDetailsUseCase: GetArticleDetailsUseCase = mockk()
    private var state = SavedStateHandle(mapOf(ARTICLE_ID to "articeId"))
    private val tested by lazy {
        ArticleDetailsViewModel(articleDetailsUseCase, state)
    }

    @Test
    fun `verify that init execute as expected`() {
        // given
        every {
            articleDetailsUseCase.getArticle(any())
        } returns Single.just(mockk(relaxed = true))

        // when
        tested

        // then
        verify(exactly = 1) { articleDetailsUseCase.getArticle(any()) }
    }

    @Test
    fun `verify that we get expected live data in case of success`() {
        // given
        val article = ArticleFixtures.Builder(
            body = "<p>some html</p>"
        ).build()
        every {
            articleDetailsUseCase.getArticle(any())
        } returns Single.just(article)

        // when
        val articleDataObserver = tested.articleDetailsData.test()
        val errorObserver = tested.isFailure.test()

        // then
        errorObserver.assertHasValue {
            // not a failure
            !it
        }
        articleDataObserver.assertHasValue {
            it.id == article.id
        }
    }

    @Test
    fun `verify that we get expected live data in case of failure`() {
        // given
        every {
            articleDetailsUseCase.getArticle(any())
        } returns Single.error(Exception("some error"))

        // when
        val articleDataObserver = tested.articleDetailsData.test()
        val errorObserver = tested.isFailure.test()

        // then
        errorObserver.assertHasValue {
            // it's a failure
            it
        }
        articleDataObserver.assertNoValue()
    }
}
