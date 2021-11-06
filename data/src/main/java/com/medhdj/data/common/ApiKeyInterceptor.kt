package com.medhdj.data.common

import com.medhdj.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val request = original.newBuilder()
            .header(API_KEY, BuildConfig.THEGUARDIAN_API_KEY)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "api-key"
    }

}
