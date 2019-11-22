package com.showmenews.di.component

import android.content.Context
import com.showmenews.arch.repo.RemoteRepository
import com.showmenews.di.module.AppModule
import com.showmenews.di.module.DataModule
import com.showmenews.di.module.NetworkModule
import com.showmenews.usecase.base.StatefulViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        AppModule::class,
        DataModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun context(): Context
    fun repository(): RemoteRepository

    fun inject(statefulViewModel: StatefulViewModel)
}