package com.toters.marvelfan.di

import com.toters.marvelfan.data.network.api.APIServiceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//private const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
//
//val networkModule = module {
//    single {
//        APIServiceProvider().provide(androidContext(), BASE_URL)
//    }
//}