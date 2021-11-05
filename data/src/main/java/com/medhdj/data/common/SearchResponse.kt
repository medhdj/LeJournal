package com.medhdj.data.common

data class SearchResponse<T>(
    val pageSize: Int,
    val startIndex: Int,
    val currentPage: Int,
    val results: List<T>
)
