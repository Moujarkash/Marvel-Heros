package com.mod.marvelcomic.infrastructure.repositories

import androidx.paging.*
import com.mod.marvelcomic.domain.models.Comic
import com.mod.marvelcomic.domain.models.ComicCharacter
import com.mod.marvelcomic.domain.models.Event
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.datasources.CharacterComicRemoteMediator
import com.mod.marvelcomic.infrastructure.datasources.CharacterEventRemoteMediator
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteDataSource
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteMediator
import com.mod.marvelcomic.infrastructure.entities.toComic
import com.mod.marvelcomic.infrastructure.entities.toComicCharacter
import com.mod.marvelcomic.infrastructure.entities.toEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ComicCharacterRepositoryImpl @Inject constructor(
    private val comicCharacterRemoteDataSource: ComicCharacterRemoteDataSource,
    private val database: AppDatabase
) : ComicCharacterRepository {
    override fun getComicCharacters(limit: Int): Flow<PagingData<ComicCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = ComicCharacterRemoteMediator(database, comicCharacterRemoteDataSource)
        ) {
            database.comicCharacterDao().pagingSource()
        }.flow.map { entities -> entities.map { it.toComicCharacter() } }
    }

    override suspend fun getComicCharacter(id: Int): ComicCharacter? {
        return database.comicCharacterDao().getCharacter(id)?.toComicCharacter()
    }

    override fun getCharacterComics(characterId: Int, limit: Int): Flow<PagingData<Comic>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = CharacterComicRemoteMediator(
                characterId,
                database,
                comicCharacterRemoteDataSource
            )
        ) {
            database.characterComicDao().pagingSource(characterId)
        }.flow.map { entities -> entities.map { it.toComic() } }
    }

    override fun getCharacterEvents(characterId: Int, limit: Int): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = CharacterEventRemoteMediator(
                characterId,
                database,
                comicCharacterRemoteDataSource
            )
        ) {
            database.characterEventDao().pagingSource(characterId)
        }.flow.map { entities -> entities.map { it.toEvent() } }
    }
}