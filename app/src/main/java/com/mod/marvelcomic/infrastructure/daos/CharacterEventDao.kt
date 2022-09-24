package com.mod.marvelcomic.infrastructure.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mod.marvelcomic.infrastructure.entities.CharacterEventEntity

@Dao
interface CharacterEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterComics: List<CharacterEventEntity>)

    @Query("SELECT * FROM character_event WHERE characterId = :characterId")
    fun pagingSource(characterId: Int): PagingSource<Int, CharacterEventEntity>

    @Query("DELETE FROM character_event WHERE characterId = :characterId")
    suspend fun clearAll(characterId: Int)
}