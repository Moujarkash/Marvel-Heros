package com.mod.marvelcomic.domain.repositories

import androidx.paging.PagingData
import com.mod.marvelcomic.domain.models.*
import kotlinx.coroutines.flow.Flow

interface ComicCharacterRepository {
    fun getComicCharacters(limit: Int): Flow<PagingData<ComicCharacter>>
    suspend fun getComicCharacter(id: Int): ComicCharacter?
    fun getCharacterComics(characterId: Int, limit: Int): Flow<PagingData<Comic>>
    fun getCharacterEvents(characterId: Int, limit: Int): Flow<PagingData<Event>>
    fun getCharacterSeries(characterId: Int, limit: Int): Flow<PagingData<Series>>
    fun getCharacterStories(characterId: Int, limit: Int): Flow<PagingData<Story>>
}