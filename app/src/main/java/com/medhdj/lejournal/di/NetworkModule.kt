package com.medhdj.lejournal.di

import com.medhdj.data.common.ApiKeyInterceptor
import com.medhdj.data.common.TheGuardianApi
import com.medhdj.lejournal.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TheGuardianApi.BASE_URL)
            .client(createOktthp())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGuardianApi(retrofit: Retrofit): TheGuardianApi =
        retrofit.create(TheGuardianApi::class.java)

    private fun createOktthp(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addInterceptor(ApiKeyInterceptor())
            }

            build()
        }
    }
}
