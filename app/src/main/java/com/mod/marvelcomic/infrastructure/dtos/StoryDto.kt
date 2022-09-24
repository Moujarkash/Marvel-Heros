package com.mod.marvelcomic.infrastructure.dtos

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailDto?
)
