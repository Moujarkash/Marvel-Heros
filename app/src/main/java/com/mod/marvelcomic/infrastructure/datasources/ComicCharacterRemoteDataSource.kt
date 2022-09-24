package com.mod.marvelcomic.infrastructure.datasources

import com.mod.marvelcomic.BuildConfig
import com.mod.marvelcomic.infrastructure.apiservices.ComicCharacterApiService
import com.mod.marvelcomic.infrastructure.core.ResponseWrapper
import com.mod.marvelcomic.infrastructure.core.md5
import com.mod.marvelcomic.infrastructure.dtos.ComicCharacterDto
import javax.inject.Inject

interface ComicCharacterRemoteDataSource {
    suspend fun getComicCharacters(offset: Int, limit: Int): ResponseWrapper<ComicCharacterDto>
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
}