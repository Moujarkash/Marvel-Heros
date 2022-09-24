package com.mod.marvelcomic.infrastructure.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mod.marvelcomic.infrastructure.daos.*
import com.mod.marvelcomic.infrastructure.entities.*

@Database(
    entities = [ComicCharacterEntity::class, RemoteKeyEntity::class, CharacterComicEntity::class, CharacterEventEntity::class, CharacterSeriesEntity::class, CharacterStoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicCharacterDao(): ComicCharacterDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun characterComicDao(): CharacterComicDao
    abstract fun characterEventDao(): CharacterEventDao
    abstract fun characterSeriesDao(): CharacterSeriesDao
    abstract fun characterStoryDao(): CharacterStoryDao
}