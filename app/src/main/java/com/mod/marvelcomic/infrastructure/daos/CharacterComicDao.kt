package com.mod.marvelcomic.infrastructure.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mod.marvelcomic.infrastructure.entities.CharacterComicEntity

@Dao
interface CharacterComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterComics: List<CharacterComicEntity>)

    @Query("SELECT * FROM character_comic WHERE characterId = :characterId")
    fun pagingSource(characterId: Int): PagingSource<Int, CharacterComicEntity>

    @Query("DELETE FROM character_comic WHERE characterId = :characterId")
    suspend fun clearAll(characterId: Int)
}