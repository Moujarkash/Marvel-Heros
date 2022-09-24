package com.mod.marvelcomic.domain.usecases

import androidx.paging.PagingData
import com.mod.marvelcomic.domain.models.Series
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterSeriesUseCase @Inject constructor(private val comicCharacterRepository: ComicCharacterRepository) {
    operator fun invoke(characterId: Int, limit: Int): Flow<PagingData<Series>> {
        return comicCharacterRepository.getCharacterSeries(characterId, limit)
    }
}