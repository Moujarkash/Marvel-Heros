package com.mod.marvelcomic.infrastructure.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.entities.CharacterSeriesEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyType
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterSeriesRemoteMediator(
    private val characterId: Int,
    private val database: AppDatabase,
    private val remoteDataSource: ComicCharacterRemoteDataSource
) : RemoteMediator<Int, CharacterSeriesEntity>() {
    private val characterSeriesDao = database.characterSeriesDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterSeriesEntity>
    ): MediatorResult {
        return try {
            val limit = state.config.pageSize

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey(RemoteKeyType.CharacterSeries, characterId)
                    }

                    if (remoteKey?.offset == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.offset
                }
            }

            val response = remoteDataSource.getCharacterSeries(characterId, offset, limit)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterSeriesDao.clearAll(characterId)
                    remoteKeyDao.delete(RemoteKeyType.CharacterSeries, characterId)
                }

                remoteKeyDao.insertOrReplace(RemoteKeyEntity(characterId, RemoteKeyType.CharacterSeries,  response.data.offset + limit))

                characterSeriesDao.insertAll(response.data.results.map { characterSeriesDto -> CharacterSeriesEntity(
                    id = characterSeriesDto.id,
                    characterId = characterId,
                    title = characterSeriesDto.title,
                    description = characterSeriesDto.description,
                    thumbnail = characterSeriesDto.thumbnail?.getFullUrl() ?: ""
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