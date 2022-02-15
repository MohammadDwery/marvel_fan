package com.toters.marvelfan.di

import com.toters.marvelfan.ui.character_detail.CharacterDetailViewModel
import com.toters.marvelfan.ui.characters.CharactersViewModel
import com.toters.marvelfan.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { CharactersViewModel(charactersRepository = get()) }
    viewModel { MainViewModel() }
    viewModel { CharacterDetailViewModel(charactersRepository = get()) }
}
