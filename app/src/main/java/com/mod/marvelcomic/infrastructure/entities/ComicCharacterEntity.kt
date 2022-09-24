package com.mod.marvelcomic.infrastructure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mod.marvelcomic.domain.models.ComicCharacter

@Entity(tableName = "comic_characters")
data class ComicCharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int = 0,
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String
)

fun ComicCharacterEntity.toComicCharacter() = ComicCharacter(
    id = id, name = name, description = description, thumbnail = thumbnail
)
