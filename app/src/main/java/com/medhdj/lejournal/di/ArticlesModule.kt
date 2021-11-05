package com.medhdj.lejournal.di

import com.medhdj.business.articles.ArticlesFeedRepository
import com.medhdj.business.articles.GetArticlesFeedUseCase
import com.medhdj.business.articles.GetArticlesFeedUseCaseImpl
import com.medhdj.data.articles.ArticlesFeedRepositoryImpl
import com.medhdj.data.common.GuardianApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ArticlesModule {

    @Provides
    @ActivityScoped
    fun provideGetArticlesFeedUseCase(articlesFeedRepository: ArticlesFeedRepository): GetArticlesFeedUseCase =
        GetArticlesFeedUseCaseImpl(articlesFeedRepository)

    @Provides
    @ActivityScoped
    fun provideArticlesFeedRepository(guardianApi: GuardianApi): ArticlesFeedRepository =
        ArticlesFeedRepositoryImpl(guardianApi)

}