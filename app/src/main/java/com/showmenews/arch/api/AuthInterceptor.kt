package com.showmenews.arch.api

import com.showmenews.BuildConfig
import com.showmenews.utils.API_KEY_FIELD
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        val url =
            request.url()
                .newBuilder()
                .addQueryParameter(API_KEY_FIELD, BuildConfig.API_KEY)
                .build()

        return chain.proceed(builder.url(url).build())
    }
}