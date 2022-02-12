package com.toters.marvelfan.data.network.api

import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.data.network.resposes.Response
import com.toters.marvelfan.data.paging.CHARACTERS_LIMIT
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPIServices {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = CHARACTERS_LIMIT,
    ) : Response<CharacterModel>

    suspend fun getSingleCharacter(id: Int)
}