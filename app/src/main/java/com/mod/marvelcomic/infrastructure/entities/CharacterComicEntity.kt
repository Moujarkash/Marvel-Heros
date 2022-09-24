package com.mod.marvelcomic.infrastructure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mod.marvelcomic.domain.models.Comic

@Entity(tableName = "character_comic")
data class CharacterComicEntity(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int = 0,
    val id: Int,
    val characterId: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)

fun CharacterComicEntity.toComic() = Comic(
    id = id, title = title, description = description ?: "", thumbnail = thumbnail
)
