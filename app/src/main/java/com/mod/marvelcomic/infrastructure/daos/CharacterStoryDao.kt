package com.mod.marvelcomic.infrastructure.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mod.marvelcomic.infrastructure.entities.CharacterStoryEntity

@Dao
interface CharacterStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterComics: List<CharacterStoryEntity>)

    @Query("SELECT * FROM character_story WHERE characterId = :characterId")
    fun pagingSource(characterId: Int): PagingSource<Int, CharacterStoryEntity>

    @Query("DELETE FROM character_story WHERE characterId = :characterId")
    suspend fun clearAll(characterId: Int)
}