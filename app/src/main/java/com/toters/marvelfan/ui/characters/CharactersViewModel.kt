package com.toters.marvelfan.ui.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.toters.marvelfan.ui.base.BaseViewModel
import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(
    private val charactersRepository: CharactersRepository
) : BaseViewModel() {
    private lateinit var _charactersFlow: Flow<PagingData<CharacterModel>>
    val charactersFlow: Flow<PagingData<CharacterModel>>
        get() = _charactersFlow

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() = launchPagingAsync(
        execute = { charactersRepository.getCharacters().cachedIn(viewModelScope) },
        onSuccess = { _charactersFlow = it },
    )
}