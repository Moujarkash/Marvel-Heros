package com.mod.marvelcomic.domain.repositories

import androidx.paging.PagingData
import com.mod.marvelcomic.domain.models.ComicCharacter
import kotlinx.coroutines.flow.Flow

interface ComicCharacterRepository {
    fun getComicCharacters(limit: Int): Flow<PagingData<ComicCharacter>>
}