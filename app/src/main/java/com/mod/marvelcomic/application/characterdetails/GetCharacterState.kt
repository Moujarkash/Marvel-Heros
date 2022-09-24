package com.mod.marvelcomic.application.characterdetails

import com.mod.marvelcomic.domain.models.ComicCharacter

sealed class GetCharacterState {
    object Idle: GetCharacterState()
    object Loading: GetCharacterState()
    data class Success(val data: ComicCharacter?): GetCharacterState()
}
