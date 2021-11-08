package com.medhdj.data.common

import com.google.gson.annotations.SerializedName

data class SingleItemResponse<T>(
    @SerializedName("response")
    val data: SingleItemData<T>
)

data class SingleItemData<T>(
    val content: T
)
