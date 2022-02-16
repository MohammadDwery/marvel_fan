package com.toters.marvelfan.ui.character_detail

import androidx.lifecycle.viewModelScope
import com.toters.marvelfan.data.model.*
import com.toters.marvelfan.data.network.DataResourceState
import com.toters.marvelfan.data.repository.CharactersRepository
import com.toters.marvelfan.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val charactersRepository: CharactersRepository
) : BaseViewModel() {
    private lateinit var _characterDetailsDataResFlow: Flow<DataResourceState<CharacterModel>>
    val characterDetailsDataResFlow: Flow<DataResourceState<CharacterModel>>
        get() = _characterDetailsDataResFlow

    private lateinit var _comicsDataResFlow: Flow<DataResourceState<List<ComicsModel>>>
    val comicsDataResFlow: Flow<DataResourceState<List<ComicsModel>>>
        get() = _comicsDataResFlow

    private lateinit var _eventsDataResFlow: Flow<DataResourceState<List<EventModel>>>
    val eventsDataResFlow: Flow<DataResourceState<List<EventModel>>>
        get() = _eventsDataResFlow

    private lateinit var _seriesDataResFlow: Flow<DataResourceState<List<SerieModel>>>
    val seriesDataResFlow: Flow<DataResourceState<List<SerieModel>>>
        get() = _seriesDataResFlow

    private lateinit var _storiesDataResFlow: Flow<DataResourceState<List<StoryModel>>>
    val storiesDataResFlow: Flow<DataResourceState<List<StoryModel>>>
        get() = _storiesDataResFlow

    fun getCharacter(id: Int) = viewModelScope.launch {
        _characterDetailsDataResFlow = launchFetchItemAsync { charactersRepository.getSingleCharacter(id) }
    }

    fun getCharacterComics(id: Int) = viewModelScope.launch {
        _comicsDataResFlow = launchFetchListAsync { charactersRepository.getCharacterComics(id) }
    }

    fun getCharacterEvents(id: Int) = viewModelScope.launch {
        _eventsDataResFlow = launchFetchListAsync { charactersRepository.getCharacterEvents(id) }
    }

    fun getCharacterSeries(id: Int) = viewModelScope.launch {
        _seriesDataResFlow = launchFetchListAsync { charactersRepository.getCharacterSeries(id) }
    }

    fun getCharacterStories(id: Int) = viewModelScope.launch {
        _storiesDataResFlow = launchFetchListAsync { charactersRepository.getCharacterStories(id) }
    }
}