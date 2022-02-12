package com.toters.marvelfan.di

import com.toters.marvelfan.data.repository.CharactersRepository
import com.toters.marvelfan.data.repository.CharactersRepositoryImp
import org.koin.dsl.module

val repoModule = module {
    factory<CharactersRepository> { CharactersRepositoryImp(charactersApiServices = get()) }
}
