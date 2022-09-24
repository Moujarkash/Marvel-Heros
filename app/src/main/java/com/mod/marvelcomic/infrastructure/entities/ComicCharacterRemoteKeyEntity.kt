package com.mod.marvelcomic.infrastructure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comic_character_remote_key")
data class ComicCharacterRemoteKeyEntity(
    @PrimaryKey val id: Int,
    val offset: Int?
)
