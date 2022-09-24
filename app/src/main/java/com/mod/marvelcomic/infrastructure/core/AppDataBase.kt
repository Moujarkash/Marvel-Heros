package com.mod.marvelcomic.infrastructure.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mod.marvelcomic.infrastructure.daos.CharacterComicDao
import com.mod.marvelcomic.infrastructure.daos.CharacterEventDao
import com.mod.marvelcomic.infrastructure.daos.ComicCharacterDao
import com.mod.marvelcomic.infrastructure.daos.RemoteKeyDao
import com.mod.marvelcomic.infrastructure.entities.CharacterComicEntity
import com.mod.marvelcomic.infrastructure.entities.CharacterEventEntity
import com.mod.marvelcomic.infrastructure.entities.ComicCharacterEntity
import com.mod.marvelcomic.infrastructure.entities.RemoteKeyEntity

@Database(entities = [ComicCharacterEntity::class, RemoteKeyEntity::class, CharacterComicEntity::class, CharacterEventEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicCharacterDao(): ComicCharacterDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun characterComicDao(): CharacterComicDao
    abstract fun characterEventDao(): CharacterEventDao
}