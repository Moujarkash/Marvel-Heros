package com.mod.marvelcomic.application.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mod.marvelcomic.domain.models.Comic
import com.mod.marvelcomic.domain.models.Event
import com.mod.marvelcomic.domain.models.Series
import com.mod.marvelcomic.domain.models.Story
import com.mod.marvelcomic.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel@Inject constructor(
    private val getComicCharacterUseCase: GetComicCharacterUseCase,
    getCharacterComicsUseCase: GetCharacterComicsUseCase,
    getCharacterEventsUseCase: GetCharacterEventsUseCase,
    getCharacterSeriesUseCase: GetCharacterSeriesUseCase,
    getCharacterStoriesUseCase: GetCharacterStoriesUseCase,
    state: SavedStateHandle
) : ViewModel() {
    val characterId = state.get<Int>("characterId") ?: -1

    private val _characterState: MutableStateFlow<GetCharacterState> = MutableStateFlow(GetCharacterState.Idle)
    val characterState: StateFlow<GetCharacterState> = _characterState

    val comics: Flow<PagingData<Comic>> =
        getCharacterComicsUseCase(characterId, 3).cachedIn(viewModelScope)

    val events: Flow<PagingData<Event>> =
        getCharacterEventsUseCase(characterId, 3).cachedIn(viewModelScope)

    val series: Flow<PagingData<Series>> =
        getCharacterSeriesUseCase(characterId, 3).cachedIn(viewModelScope)

    val stories: Flow<PagingData<Story>> =
        getCharacterStoriesUseCase(characterId, 3).cachedIn(viewModelScope)

    init {
        getCharacter()
    }

    private fun getCharacter() {
        viewModelScope.launch {
            _characterState.value = GetCharacterState.Loading
            _characterState.value = GetCharacterState.Success(getComicCharacterUseCase(characterId))
        }
    }
}