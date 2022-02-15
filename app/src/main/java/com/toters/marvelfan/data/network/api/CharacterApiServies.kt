package com.toters.marvelfan.data.network.api

import com.toters.marvelfan.data.model.*
import com.toters.marvelfan.data.network.resposes.Response
import com.toters.marvelfan.data.paging.CHARACTERS_LIMIT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val COMICS_LIMIT = 3
const val EVENTS_LIMIT = 3
const val SERIES_LIMIT = 3
const val STORIES_LIMIT = 3

interface CharactersAPIServices {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = CHARACTERS_LIMIT,
    ) : Response<CharacterModel>

    @GET("characters/{characterId}")
    suspend fun getSingleCharacter(
        @Path("characterId") id: Int
    ) : Response<CharacterModel>

    @GET("characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = COMICS_LIMIT,
    ) : Response<ComicsModel>

    @GET("characters/{characterId}/events")
    suspend fun getCharacterEvents(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = EVENTS_LIMIT,
    ) : Response<EventModel>

    @GET("characters/{characterId}/stories")
    suspend fun getCharacterStories(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = STORIES_LIMIT,
    ) : Response<StoryModel>

    @GET("characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = SERIES_LIMIT,
    ) : Response<SerieModel>
}