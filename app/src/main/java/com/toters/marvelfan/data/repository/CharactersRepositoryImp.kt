package com.toters.marvelfan.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.data.network.api.CharactersAPIServices
import com.toters.marvelfan.data.paging.CHARACTERS_LIMIT
import com.toters.marvelfan.data.paging.CharactersPagingSource
import kotlinx.coroutines.flow.Flow

class CharactersRepositoryImp(
    private val charactersApiServices: CharactersAPIServices
): CharactersRepository {
    override suspend fun getCharacters(): Flow<PagingData<CharacterModel>> = Pager(
        config = PagingConfig(pageSize = CHARACTERS_LIMIT, prefetchDistance = 2),
        pagingSourceFactory = { CharactersPagingSource(charactersApiServices) }
    ).flow

    override suspend fun getSingleCharacter(id: Int) = charactersApiServices.getSingleCharacter(id)
}