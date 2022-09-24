package com.mod.marvelcomic.infrastructure.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.dtos.toComicCharacterEntity
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterEntity
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterRemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ComicCharacterRemoteMediator(
    private val database: AppDatabase,
    private val remoteDataSource: ComicCharacterRemoteDataSource
): RemoteMediator<Int, ComicCharacterEntity>() {
    private val comicCharacterDao = database.comicCharacterDao()
    private val comicCharacterRemoteKeyDao = database.comicCharacterRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ComicCharacterEntity>
    ): MediatorResult {
        return try {
            val limit = state.config.pageSize

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        comicCharacterRemoteKeyDao.remoteKeyById(1)
                    }

                    if (remoteKey.offset == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.offset
                }
            }

            val response = remoteDataSource.getComicCharacters(offset, limit)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    comicCharacterDao.clearAll()
                    comicCharacterRemoteKeyDao.deleteById(1)
                }

                comicCharacterRemoteKeyDao.insertOrReplace(ComicCharacterRemoteKeyEntity(1, response.data.offset + limit))

                comicCharacterDao.insertAll(response.data.results.map { it.toComicCharacterEntity() })
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