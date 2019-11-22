package com.showmenews.di.module

import com.showmenews.arch.repo.RemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(retrofit: Retrofit): RemoteRepository =
        RemoteRepository(retrofit)

}