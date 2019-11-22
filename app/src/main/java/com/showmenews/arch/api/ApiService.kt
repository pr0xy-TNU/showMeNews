package com.showmenews.arch.api

import com.showmenews.data.ApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("articlesearch.json")
    fun getNews(@QueryMap requestData: HashMap<String, Any?>): Single<Response<ApiResponse>>
}