package com.medhdj.lejournal.di

import com.medhdj.business.articles.ArticlesFeedRepository
import com.medhdj.business.articles.GetArticlesFeedUseCase
import com.medhdj.business.articles.GetArticlesFeedUseCaseImpl
import com.medhdj.data.articles.ArticlesFeedDataSource
import com.medhdj.data.articles.ArticlesFeedRepositoryImpl
import com.medhdj.data.common.TheGuardianApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ArticlesModule {

    @Provides
    fun provideGetArticlesFeedUseCase(articlesFeedRepository: ArticlesFeedRepository): GetArticlesFeedUseCase =
        GetArticlesFeedUseCaseImpl(articlesFeedRepository)

    @Provides
    fun provideArticlesFeedRepository(articlesFeedDataSource: ArticlesFeedDataSource): ArticlesFeedRepository =
        ArticlesFeedRepositoryImpl(articlesFeedDataSource)

    @Provides
    fun provideArticlesFeedDataSource(theGuardianApi: TheGuardianApi): ArticlesFeedDataSource =
        ArticlesFeedDataSource(theGuardianApi)

}
