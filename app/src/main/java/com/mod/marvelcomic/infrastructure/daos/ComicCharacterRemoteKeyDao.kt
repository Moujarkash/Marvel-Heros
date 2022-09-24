package com.mod.marvelcomic.infrastructure.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterRemoteKeyEntity

@Dao
interface ComicCharacterRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: ComicCharacterRemoteKeyEntity)

    @Query("SELECT * FROM comic_character_remote_key WHERE id = :id")
    suspend fun remoteKeyById(id: Int): ComicCharacterRemoteKeyEntity

    @Query("DELETE FROM comic_character_remote_key WHERE id = :id")
    suspend fun deleteById(id: Int)
}