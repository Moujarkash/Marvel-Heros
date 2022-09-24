package com.mod.marvelcomic.domain.usecases

import com.mod.marvelcomic.domain.models.ComicCharacter
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import javax.inject.Inject

class GetComicCharacterUseCase @Inject constructor(private val comicCharacterRepository: ComicCharacterRepository) {
    suspend operator fun invoke(id: Int): ComicCharacter? {
        return comicCharacterRepository.getComicCharacter(id)
    }
}