package com.mod.marvelcomic.infrastructure.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyType

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE type = :type")
    suspend fun getRemoteKey(type: RemoteKeyType): RemoteKeyEntity

    @Query("SELECT * FROM remote_key WHERE type = :type AND id = :id")
    suspend fun getRemoteKey(type: RemoteKeyType, id: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_key WHERE type = :type")
    suspend fun delete(type: RemoteKeyType)

    @Query("DELETE FROM remote_key WHERE type = :type AND id = :id")
    suspend fun delete(type: RemoteKeyType, id: Int)
}