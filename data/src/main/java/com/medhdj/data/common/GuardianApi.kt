package com.medhdj.data.common

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GuardianApi {

    @GET("/{endpoint}")
    fun <T> search(
        @Path("endpoint") endpoint: SearchableEndpoint,
        @Query("q") withContent: String,
        @Query("pageSize") pageSize: Int,
        @QueryMap extraInformation: Map<String, String>
    ): Observable<SearchResponse<T>>

    companion object {
        const val BASE_URL = "https://content.guardianapis.com"
    }
}

enum class SearchableEndpoint(private val value: String) {
    CONTENT_ENDPOINT("search"),
    SECTIONS_ENDPOINT("sections"),
    TAGS_ENDPOINT("tags");

    override fun toString(): String = value
}

