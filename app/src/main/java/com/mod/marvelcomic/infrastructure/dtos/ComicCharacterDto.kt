package com.mod.marvelcomic.infrastructure.dtos

import com.mod.marvelcomic.infrastructure.entities.ComicCharacterEntity
import kotlinx.serialization.Serializable

@Serializable
data class ComicCharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDto
)

fun ComicCharacterDto.toComicCharacterEntity() = ComicCharacterEntity(
    id = id, name = name, description = description, thumbnail = thumbnail.getFullUrl()
)