package com.mod.marvelcomic.infrastructure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mod.marvelcomic.domain.models.Series

@Entity(tableName = "character_series")
data class CharacterSeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int = 0,
    val id: Int,
    val characterId: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)

fun CharacterSeriesEntity.toSeries() = Series(
    id = id, title = title, description = description ?: "", thumbnail = thumbnail
)
