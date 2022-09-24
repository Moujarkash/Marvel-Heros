package com.mod.marvelcomic.infrastructure.datasources

import com.mod.marvelcomic.BuildConfig
import com.mod.marvelcomic.infrastructure.apiservices.ComicCharacterApiService
import com.mod.marvelcomic.infrastructure.core.ResponseWrapper
import com.mod.marvelcomic.infrastructure.core.md5
import com.mod.marvelcomic.infrastructure.dtos.ComicCharacterDto
import com.mod.marvelcomic.infrastructure.dtos.ComicDto
import com.mod.marvelcomic.infrastructure.dtos.EventDto
import javax.inject.Inject

interface ComicCharacterRemoteDataSource {
    suspend fun getComicCharacters(offset: Int, limit: Int): ResponseWrapper<ComicCharacterDto>
    suspend fun getCharacterComics(characterId: Int, offset: Int, limit: Int): ResponseWrapper<ComicDto>
    suspend fun getCharacterEvents(characterId: Int, offset: Int, limit: Int): ResponseWrapper<EventDto>
}

class ComicCharacterRemoteDataSourceImpl @Inject constructor(
    private val comicCharacterApiService: ComicCharacterApiService
): ComicCharacterRemoteDataSource {
    override suspend fun getComicCharacters(
        offset: Int,
        limit: Int
    ): ResponseWrapper<ComicCharacterDto> {
        val timestamp = System.currentTimeMillis()
        val hash = md5("$timestamp${BuildConfig.API_PRIVATE_KEY}${BuildConfig.API_KEY}")
        return comicCharacterApiService.getComicCharacters(
            offset, limit, timestamp, hash
        )
    }

    override suspend fun getCharacterComics(
        characterId: Int,
        offset: Int,
        limit: Int
    ): ResponseWrapper<ComicDto> {
        val timestamp = System.currentTimeMillis()
        val hash = md5("$timestamp${BuildConfig.API_PRIVATE_KEY}${BuildConfig.API_KEY}")
        return comicCharacterApiService.getCharacterComics(characterId, offset, limit, timestamp, hash)
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        offset: Int,
        limit: Int
    ): ResponseWrapper<EventDto> {
        val timestamp = System.currentTimeMillis()
        val hash = md5("$timestamp${BuildConfig.API_PRIVATE_KEY}${BuildConfig.API_KEY}")
        return comicCharacterApiService.getCharacterEvents(characterId, offset, limit, timestamp, hash)
    }
}