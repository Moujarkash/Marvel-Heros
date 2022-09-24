package com.mod.marvelcomic.infrastructure.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.entities.CharacterStoryEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyType
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterStoryRemoteMediator(
private val characterId: Int,
private val database: AppDatabase,
private val remoteDataSource: ComicCharacterRemoteDataSource
) : RemoteMediator<Int, CharacterStoryEntity>() {
    private val characterStoryDao = database.characterStoryDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterStoryEntity>
    ): MediatorResult {
        return try {
            val limit = state.config.pageSize

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey(RemoteKeyType.CharacterStory, characterId)
                    }

                    if (remoteKey?.offset == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.offset
                }
            }

            val response = remoteDataSource.getCharacterStories(characterId, offset, limit)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterStoryDao.clearAll(characterId)
                    remoteKeyDao.delete(RemoteKeyType.CharacterStory, characterId)
                }

                remoteKeyDao.insertOrReplace(RemoteKeyEntity(characterId, RemoteKeyType.CharacterStory,  response.data.offset + limit))

                characterStoryDao.insertAll(response.data.results.map { characterStoryDto -> CharacterStoryEntity(
                    id = characterStoryDto.id,
                    characterId = characterId,
                    title = characterStoryDto.title,
                    description = characterStoryDto.description,
                    thumbnail = characterStoryDto.thumbnail?.getFullUrl() ?: ""
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