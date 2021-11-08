package com.medhdj.data.common

import com.google.gson.annotations.SerializedName

data class SearchResponse<T>(
    @SerializedName("response")
    val data: SearchData<T>
)

data class SearchData<T>(
    val pageSize: Int,
    val currentPage: Int,
    @SerializedName("pages")
    val totalPages: Int,
    val results: List<T>
)
