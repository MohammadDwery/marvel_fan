package com.toters.marvelfan.data.repository

import androidx.paging.PagingData
import com.toters.marvelfan.data.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(): Flow<PagingData<CharacterModel>>
    suspend fun getSingleCharacter(id: Int)
}