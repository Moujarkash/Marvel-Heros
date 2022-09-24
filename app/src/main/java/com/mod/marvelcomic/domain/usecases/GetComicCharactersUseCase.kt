package com.mod.marvelcomic.domain.usecases

import androidx.paging.PagingData
import com.mod.marvelcomic.domain.models.ComicCharacter
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComicCharactersUseCase @Inject constructor(private val comicCharacterRepository: ComicCharacterRepository) {
    operator fun invoke(limit: Int): Flow<PagingData<ComicCharacter>> {
        return comicCharacterRepository.getComicCharacters(limit)
    }
}