package com.mod.marvelcomic.application.comiccharacters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
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
        TopAppBar {
            Text(text = "Users")
        }
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentPadding = PaddingValues(16.dp)
        ) {
            items(comicCharacters) { user ->
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = user?.thumbnail, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = user?.name ?: "")
                }
            }

            comicCharacters.apply {
                when {
                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }
                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                        item { Text(text = "Error") }
                    }
                }
            }
        }
    }
}