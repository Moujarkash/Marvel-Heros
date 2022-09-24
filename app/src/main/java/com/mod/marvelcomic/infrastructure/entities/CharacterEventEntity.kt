package com.mod.marvelcomic.infrastructure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mod.marvelcomic.domain.models.Event

@Entity(tableName = "character_event")
data class CharacterEventEntity(
    @PrimaryKey(autoGenerate = true)
    val dbId: Int = 0,
    val id: Int,
    val characterId: Int,
    val title: String,
    val description: String?,
    val thumbnail: String
)

fun CharacterEventEntity.toEvent() = Event(
    id = id, title = title, description = description ?: "", thumbnail = thumbnail
)
