package com.showmenews

import android.app.Application
import android.content.Context
import com.showmenews.di.component.AppComponent
import com.showmenews.di.component.DaggerAppComponent
import com.showmenews.di.module.AppModule
import com.showmenews.di.module.DataModule
import com.showmenews.di.module.NetworkModule

class App : Application(){
    companion object {
        lateinit var appComponent: AppComponent
        val context: Context
            get() = appComponent.context()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .networkModule(NetworkModule())
            .build()
    }
}