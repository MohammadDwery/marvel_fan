package com.toters.marvelfan

import android.app.Application
import com.toters.marvelfan.di.apiServicesModule
import com.toters.marvelfan.di.repoModule
import com.toters.marvelfan.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MarvelApplication : Application() {

    val LOG_TAG = "MarvelApplication"

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MarvelApplication)
            modules(apiServicesModule, repoModule, viewModelsModule)
        }
    }
}