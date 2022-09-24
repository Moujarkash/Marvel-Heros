package com.mod.marvelcomic.infrastructure.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mod.marvelcomic.infrastructure.entities.CharacterSeriesEntity

@Dao
interface CharacterSeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterComics: List<CharacterSeriesEntity>)

    @Query("SELECT * FROM character_series WHERE characterId = :characterId")
    fun pagingSource(characterId: Int): PagingSource<Int, CharacterSeriesEntity>

    @Query("DELETE FROM character_series WHERE characterId = :characterId")
    suspend fun clearAll(characterId: Int)
}