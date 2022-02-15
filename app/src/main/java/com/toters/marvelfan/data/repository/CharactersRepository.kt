package com.toters.marvelfan.data.repository

import androidx.paging.PagingData
import com.toters.marvelfan.data.model.*
import com.toters.marvelfan.data.network.DataResourceState
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(): Flow<PagingData<CharacterModel>>
    suspend fun getSingleCharacter(id: Int): CharacterModel
    suspend fun getCharacterComics(id: Int): List<ComicsModel>
    suspend fun getCharacterEvents(id: Int): List<EventModel>
    suspend fun getCharacterSeries(id: Int): List<SerieModel>
    suspend fun getCharacterStories(id: Int): List<StoryModel>
}