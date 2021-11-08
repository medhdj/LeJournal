package com.medhdj.lejournal.di

import com.medhdj.business.articles.ArticleDetailsRepository
import com.medhdj.business.articles.ArticlesFeedRepository
import com.medhdj.business.articles.GetArticleDetailsUseCase
import com.medhdj.business.articles.GetArticleDetailsUseCaseImpl
import com.medhdj.business.articles.GetArticlesFeedUseCase
import com.medhdj.business.articles.GetArticlesFeedUseCaseImpl
import com.medhdj.data.articles.ArticleDetailsRepositoryImpl
import com.medhdj.data.articles.ArticlesFeedDataSource
import com.medhdj.data.articles.ArticlesFeedRepositoryImpl
import com.medhdj.data.common.TheGuardianApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ArticlesModule {

    // feed
    @Provides
    fun provideGetArticlesFeedUseCase(articlesFeedRepository: ArticlesFeedRepository): GetArticlesFeedUseCase =
        GetArticlesFeedUseCaseImpl(articlesFeedRepository)

    @Provides
    fun provideArticlesFeedRepository(articlesFeedDataSource: ArticlesFeedDataSource): ArticlesFeedRepository =
        ArticlesFeedRepositoryImpl(articlesFeedDataSource)

    @Provides
    fun provideArticlesFeedDataSource(theGuardianApi: TheGuardianApi): ArticlesFeedDataSource =
        ArticlesFeedDataSource(theGuardianApi)

    // details
    @Provides
    fun provideGetArticleDetailsUseCase(articleDetailsRepository: ArticleDetailsRepository): GetArticleDetailsUseCase =
        GetArticleDetailsUseCaseImpl(articleDetailsRepository)

    @Provides
    fun provideArticleDetailsRepository(theGuardianApi: TheGuardianApi): ArticleDetailsRepository =
        ArticleDetailsRepositoryImpl(theGuardianApi)
}
