package com.mod.marvelcomic.infrastructure.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.dtos.toComicCharacterEntity
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyType
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ComicCharacterRemoteMediator(
    private val database: AppDatabase,
    private val remoteDataSource: ComicCharacterRemoteDataSource
): RemoteMediator<Int, ComicCharacterEntity>() {
    private val comicCharacterDao = database.comicCharacterDao()
    private val remoteKeyDao = database.remoteKeyDao()

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
                        remoteKeyDao.getRemoteKey(RemoteKeyType.Character)
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
                    remoteKeyDao.delete(RemoteKeyType.Character)
                }

                remoteKeyDao.insertOrReplace(RemoteKeyEntity(1, RemoteKeyType.Character,  response.data.offset + limit))

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