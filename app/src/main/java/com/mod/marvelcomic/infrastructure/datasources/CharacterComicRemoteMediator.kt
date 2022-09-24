package com.mod.marvelcomic.infrastructure.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.entities.CharacterComicEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyType
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterComicRemoteMediator(
    private val characterId: Int,
    private val database: AppDatabase,
    private val remoteDataSource: ComicCharacterRemoteDataSource
): RemoteMediator<Int, CharacterComicEntity>() {
    private val characterComicDao = database.characterComicDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterComicEntity>
    ): MediatorResult {
        return try {
            val limit = state.config.pageSize

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey(RemoteKeyType.CharacterComic, characterId)
                    }

                    if (remoteKey.offset == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.offset
                }
            }

            val response = remoteDataSource.getCharacterComics(characterId, offset, limit)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterComicDao.clearAll(characterId)
                    remoteKeyDao.delete(RemoteKeyType.CharacterComic, characterId)
                }

                remoteKeyDao.insertOrReplace(RemoteKeyEntity(characterId, RemoteKeyType.CharacterComic,  response.data.offset + limit))

                characterComicDao.insertAll(response.data.results.map { characterComicDto -> CharacterComicEntity(
                    id = characterComicDto.id,
                    characterId = characterId,
                    title = characterComicDto.title,
                    description = characterComicDto.description,
                    thumbnail = characterComicDto.thumbnail.getFullUrl()
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