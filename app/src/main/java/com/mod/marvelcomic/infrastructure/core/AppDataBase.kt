package com.mod.marvelcomic.infrastructure.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mod.marvelcomic.infrastructure.daos.ComicCharacterDao
import com.mod.marvelcomic.infrastructure.daos.ComicCharacterRemoteKeyDao
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterEntity
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterRemoteKeyEntity

@Database(entities = [ComicCharacterEntity::class, ComicCharacterRemoteKeyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicCharacterDao(): ComicCharacterDao
    abstract fun comicCharacterRemoteKeyDao(): ComicCharacterRemoteKeyDao
}