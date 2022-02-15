package com.toters.marvelfan.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.toters.marvelfan.data.model.*
import com.toters.marvelfan.data.network.DataResourceState
import com.toters.marvelfan.data.network.api.CharactersAPIServices
import com.toters.marvelfan.data.paging.CHARACTERS_LIMIT
import com.toters.marvelfan.data.paging.CharactersPagingSource
import com.toters.marvelfan.utils.NetworkConnectionInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CharactersRepositoryImp(
    private val charactersApiServices: CharactersAPIServices
): CharactersRepository {

    override suspend fun getCharacters(): Flow<PagingData<CharacterModel>> = Pager(
        config = PagingConfig(pageSize = CHARACTERS_LIMIT, prefetchDistance = 2),
        pagingSourceFactory = { CharactersPagingSource(charactersApiServices) }
    ).flow

    override suspend fun getSingleCharacter(id: Int): CharacterModel =
        charactersApiServices.getSingleCharacter(id).data.results.first()

    override suspend fun getCharacterComics(id: Int): List<ComicsModel> =
        charactersApiServices.getCharacterComics(id).data.results

    override suspend fun getCharacterEvents(id: Int): List<EventModel> =
        charactersApiServices.getCharacterEvents(id).data.results

    override suspend fun getCharacterSeries(id: Int): List<SerieModel> =
        charactersApiServices.getCharacterSeries(id).data.results

    override suspend fun getCharacterStories(id: Int): List<StoryModel> =
        charactersApiServices.getCharacterStories(id).data.results
}