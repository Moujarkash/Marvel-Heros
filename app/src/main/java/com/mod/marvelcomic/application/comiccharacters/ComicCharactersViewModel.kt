package com.mod.marvelcomic.application.comiccharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mod.marvelcomic.domain.models.ComicCharacter
import com.mod.marvelcomic.domain.usecases.GetComicCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ComicCharactersViewModel @Inject constructor(
    getComicCharactersUseCase: GetComicCharactersUseCase
) : ViewModel() {
    val comicCharacters: Flow<PagingData<ComicCharacter>> =
        getComicCharactersUseCase(5).cachedIn(viewModelScope)
}