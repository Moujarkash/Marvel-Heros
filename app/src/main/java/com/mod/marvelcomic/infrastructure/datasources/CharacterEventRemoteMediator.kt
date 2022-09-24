package com.mod.marvelcomic.infrastructure.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.entities.CharacterEventEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyType
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterEventRemoteMediator(
    private val characterId: Int,
    private val database: AppDatabase,
    private val remoteDataSource: ComicCharacterRemoteDataSource
): RemoteMediator<Int, CharacterEventEntity>() {
    private val characterEventDao = database.characterEventDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEventEntity>
    ): MediatorResult {
        return try {
            val limit = state.config.pageSize

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey(RemoteKeyType.CharacterEvent, characterId)
                    }

                    if (remoteKey?.offset == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.offset
                }
            }

            val response = remoteDataSource.getCharacterEvents(characterId, offset, limit)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterEventDao.clearAll(characterId)
                    remoteKeyDao.delete(RemoteKeyType.CharacterEvent, characterId)
                }

                remoteKeyDao.insertOrReplace(RemoteKeyEntity(characterId, RemoteKeyType.CharacterEvent,  response.data.offset + limit))

                characterEventDao.insertAll(response.data.results.map { characterEventDto -> CharacterEventEntity(
                    id = characterEventDto.id,
                    characterId = characterId,
                    title = characterEventDto.title,
                    description = characterEventDto.description,
                    thumbnail = characterEventDto.thumbnail.getFullUrl()
                ) })
            }

            MediatorResult.Success(
                endOfPaginationReached = offset == response.data.total
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}