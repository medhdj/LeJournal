package com.medhdj.data.common

import com.medhdj.data.articles.ArticleEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface TheGuardianApi {

    @GET("/search")
    fun searchArticles(
        @Query("q") withContent: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @QueryMap extraInformation: Map<String, String>
    ): Single<SearchResponse<ArticleEntity>>

    companion object {
        const val BASE_URL = "https://content.guardianapis.com"
    }
}