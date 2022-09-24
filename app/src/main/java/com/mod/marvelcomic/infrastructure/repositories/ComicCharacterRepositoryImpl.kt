package com.mod.marvelcomic.infrastructure.repositories

import androidx.paging.*
import com.mod.marvelcomic.domain.models.ComicCharacter
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteDataSource
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteMediator
import com.mod.marvelcomic.infrastructure.entities.toComicCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ComicCharacterRepositoryImpl @Inject constructor(
    private val comicCharacterRemoteDataSource: ComicCharacterRemoteDataSource,
    private val database: AppDatabase
): ComicCharacterRepository {
    override fun getComicCharacters(limit: Int): Flow<PagingData<ComicCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = ComicCharacterRemoteMediator(database, comicCharacterRemoteDataSource)
        ) {
            database.comicCharacterDao().pagingSource()
        }.flow.map { entities -> entities.map { it.toComicCharacter() } }
    }
}