package com.mod.marvelcomic.infrastructure.core

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val code: Int,
    val status: String,
    val data: DataWrapper<T>
)