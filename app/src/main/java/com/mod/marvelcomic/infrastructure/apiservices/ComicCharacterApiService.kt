package com.mod.marvelcomic.infrastructure.apiservices

import com.mod.marvelcomic.BuildConfig
import com.mod.marvelcomic.infrastructure.core.ResponseWrapper
import com.mod.marvelcomic.infrastructure.dtos.ComicCharacterDto
import com.mod.marvelcomic.infrastructure.dtos.ComicDto
import com.mod.marvelcomic.infrastructure.dtos.EventDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicCharacterApiService {
    @GET("/v1/public/characters?apikey=${BuildConfig.API_KEY}")
    suspend fun getComicCharacters(@Query("offset") offset: Int, @Query("limit") limit: Int, @Query("ts") timestamp: Long, @Query("hash") hash: String): ResponseWrapper<ComicCharacterDto>

    @GET("/v1/public/characters/{id}/comics?apikey=${BuildConfig.API_KEY}")
    suspend fun getCharacterComics(@Path("id") characterId: Int, @Query("offset") offset: Int, @Query("limit") limit: Int, @Query("ts") timestamp: Long, @Query("hash") hash: String): ResponseWrapper<ComicDto>

    @GET("/v1/public/characters/{id}/events?apikey=${BuildConfig.API_KEY}")
    suspend fun getCharacterEvents(@Path("id") characterId: Int, @Query("offset") offset: Int, @Query("limit") limit: Int, @Query("ts") timestamp: Long, @Query("hash") hash: String): ResponseWrapper<EventDto>
}