package com.mod.marvelcomic.infrastructure.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ThumbnailDto(
    val extension: String,
    val path: String
) {
    fun getFullUrl() = "${path}.${extension}"
}