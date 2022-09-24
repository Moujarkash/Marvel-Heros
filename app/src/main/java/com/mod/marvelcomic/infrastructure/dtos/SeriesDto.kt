package com.mod.marvelcomic.infrastructure.dtos

import kotlinx.serialization.Serializable

@Serializable
data class SeriesDto(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailDto?
)
