package com.mod.marvelcomic.infrastructure.core

import kotlinx.serialization.Serializable

@Serializable
data class DataWrapper<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)
