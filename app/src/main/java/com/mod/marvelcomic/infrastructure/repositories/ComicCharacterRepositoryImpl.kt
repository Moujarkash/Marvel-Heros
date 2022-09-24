package com.mod.marvelcomic.infrastructure.repositories

import androidx.paging.*
import com.mod.marvelcomic.domain.models.*
import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.datasources.*
import com.mod.marvelcomic.infrastructure.entities.*
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

    override fun getCharacterSeries(characterId: Int, limit: Int): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = CharacterSeriesRemoteMediator(
                characterId,
                database,
                comicCharacterRemoteDataSource
            )
        ) {
            database.characterSeriesDao().pagingSource(characterId)
        }.flow.map { entities -> entities.map { it.toSeries() } }
    }

    override fun getCharacterStories(characterId: Int, limit: Int): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = CharacterStoryRemoteMediator(
                characterId,
                database,
                comicCharacterRemoteDataSource
            )
        ) {
            database.characterStoryDao().pagingSource(characterId)
        }.flow.map { entities -> entities.map { it.toStory() } }
    }
}