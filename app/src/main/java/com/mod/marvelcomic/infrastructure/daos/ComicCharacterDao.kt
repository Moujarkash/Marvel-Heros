package com.mod.marvelcomic.infrastructure.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterEntity

@Dao
interface ComicCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comicCharacters: List<ComicCharacterEntity>)

    @Query("SELECT * FROM comic_characters")
    fun pagingSource(): PagingSource<Int, ComicCharacterEntity>

    @Query("DELETE FROM comic_characters")
    suspend fun clearAll()
}