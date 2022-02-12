package com.toters.marvelfan.di

import com.toters.marvelfan.data.network.api.APIServiceProvider
import com.toters.marvelfan.data.network.api.CharactersAPIServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"

val apiServicesModule = module {
    single {
        APIServiceProvider().provide(androidContext(), BASE_URL)
    }
    single<CharactersAPIServices> {
        get<Retrofit>().create(CharactersAPIServices::class.java)
    }
}
