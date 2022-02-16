package com.toters.marvelfan.data.network.api

import com.toters.marvelfan.data.model.*
import com.toters.marvelfan.data.network.resposes.Response
import com.toters.marvelfan.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersAPIServices {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = AppConstants.CHARACTERS_LIMIT,
    ) : Response<CharacterModel>

    @GET("characters/{characterId}")
    suspend fun getSingleCharacter(
        @Path("characterId") id: Int
    ) : Response<CharacterModel>

    @GET("characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = AppConstants.COMICS_LIMIT,
    ) : Response<ComicsModel>

    @GET("characters/{characterId}/events")
    suspend fun getCharacterEvents(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = AppConstants.EVENTS_LIMIT,
    ) : Response<EventModel>

    @GET("characters/{characterId}/stories")
    suspend fun getCharacterStories(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = AppConstants.STORIES_LIMIT,
    ) : Response<StoryModel>

    @GET("characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Path("characterId") id: Int,
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = AppConstants.SERIES_LIMIT,
    ) : Response<SerieModel>
}