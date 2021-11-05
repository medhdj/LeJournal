package com.medhdj.data.articles

import com.medhdj.data.common.GuardianApi
import io.mockk.mockk
import org.junit.Test

class ArticlesFeedRepositoryImplTest {
    private val guardianApi: GuardianApi = mockk()
    val tested = ArticlesFeedRepositoryImpl(guardianApi)

    @Test
    fun `test`() {
        // given
        // when
        // then
    }
}
