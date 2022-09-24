package com.mod.marvelcomic.domain.usecases

import androidx.paging.PagingData
import com.mod.marvelcomic.domain.models.Story
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterStoriesUseCase @Inject constructor(private val comicCharacterRepository: ComicCharacterRepository) {
    operator fun invoke(characterId: Int, limit: Int): Flow<PagingData<Story>> {
        return comicCharacterRepository.getCharacterStories(characterId, limit)
    }
}