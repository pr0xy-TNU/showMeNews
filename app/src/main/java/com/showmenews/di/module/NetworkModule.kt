package com.showmenews.di.module

import com.google.gson.Gson
import com.showmenews.BuildConfig
import com.showmenews.arch.api.AuthInterceptor
import com.showmenews.utils.BASE_API_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideAuthenticationInterceptor() = AuthInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor? {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) else null

    }

    @Provides
    @Singleton
    fun okHttpClient(
        authenticationInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor?
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authenticationInterceptor)
            if (loggingInterceptor != null) addInterceptor(loggingInterceptor)
        }
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
}