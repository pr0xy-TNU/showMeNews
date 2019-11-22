package com.showmenews.arch.repo

import com.showmenews.arch.api.ApiService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

/**
 * Responsible for api service
 */
class RemoteRepository(retrofitClient: Retrofit) {
    val apiService = retrofitClient.create(ApiService::class.java)
}