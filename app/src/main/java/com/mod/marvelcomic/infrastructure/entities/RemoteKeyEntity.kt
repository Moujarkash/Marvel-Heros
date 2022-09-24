package com.mod.marvelcomic.infrastructure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeyEntity(
    @PrimaryKey val id: Int,
    val type: RemoteKeyType,
    val offset: Int?
)

enum class RemoteKeyType {
    Character, CharacterComic, CharacterEvent, CharacterSeries, CharacterStory
}