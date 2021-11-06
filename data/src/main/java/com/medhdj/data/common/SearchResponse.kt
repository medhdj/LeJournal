package com.medhdj.data.common

import com.google.gson.annotations.SerializedName

data class SearchResponse<T>(
    @SerializedName("response")
    val data: ResponseData<T>

)

data class ResponseData<T>(
    val pageSize: Int,
    val currentPage: Int,
    @SerializedName("pages")
    val totalPages: Int,
    val results: List<T>
)
