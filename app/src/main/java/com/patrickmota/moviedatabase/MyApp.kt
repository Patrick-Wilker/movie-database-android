package com.patrickmota.moviedatabase

import android.app.Application
import com.patrickmota.moviedatabase.di.networkModule
import com.patrickmota.moviedatabase.di.repositoryModule
import com.patrickmota.moviedatabase.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@MyApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}