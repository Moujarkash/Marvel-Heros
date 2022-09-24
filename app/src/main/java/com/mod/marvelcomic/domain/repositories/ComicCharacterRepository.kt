package com.mod.marvelcomic.domain.repositories

import androidx.paging.PagingData
import com.mod.marvelcomic.domain.models.Comic
import com.mod.marvelcomic.domain.models.ComicCharacter
import kotlinx.coroutines.flow.Flow

interface ComicCharacterRepository {
    fun getComicCharacters(limit: Int): Flow<PagingData<ComicCharacter>>
    suspend fun getComicCharacter(id: Int): ComicCharacter?
    fun getCharacterComics(characterId: Int, limit: Int): Flow<PagingData<Comic>>
}