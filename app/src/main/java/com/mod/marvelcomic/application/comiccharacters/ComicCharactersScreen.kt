package com.mod.marvelcomic.application.comiccharacters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mod.marvelcomic.R
import com.mod.marvelcomic.application.comiccharacters.components.ComicCharacterListItem
import com.mod.marvelcomic.application.components.ErrorComponent
import com.mod.marvelcomic.application.components.LoadingComponent
import com.mod.marvelcomic.application.destinations.CharacterDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun ComicCharactersScreen(
    viewModel: ComicCharactersViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val comicCharacters = viewModel.comicCharacters.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.characters_screen_title)) })
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = comicCharacters, key = { character ->
                character.id
            }) { character ->
                character?.let { comicCharacter ->
                    ComicCharacterListItem(comicCharacter = comicCharacter) {
                        navigator.navigate(CharacterDetailsScreenDestination(comicCharacter.id, comicCharacter.name))
                    }
                }
            }

            comicCharacters.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingComponent(modifier = Modifier.fillMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingComponent(modifier = Modifier.fillMaxWidth()) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item { ErrorComponent(modifier = Modifier.fillMaxSize()) }
                    }
                    loadState.append is LoadState.Error -> {
                        item { ErrorComponent(modifier = Modifier.fillMaxWidth()) }
                    }
                }
            }
        }
    }
}