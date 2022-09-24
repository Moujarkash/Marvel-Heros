package com.mod.marvelcomic.application.characterdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.mod.marvelcomic.R
import com.mod.marvelcomic.application.characterdetails.components.ComicListItem
import com.mod.marvelcomic.application.characterdetails.components.EventListItem
import com.mod.marvelcomic.application.characterdetails.components.SeriesListItem
import com.mod.marvelcomic.application.characterdetails.components.StoryListItem
import com.mod.marvelcomic.application.components.LoadingComponent
import com.mod.marvelcomic.application.components.VerticalSpace
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    characterName: String,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val characterState by viewModel.characterState.collectAsState()
    val comics = viewModel.comics.collectAsLazyPagingItems()
    val events = viewModel.events.collectAsLazyPagingItems()
    val series = viewModel.series.collectAsLazyPagingItems()
    val stories = viewModel.stories.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = characterName) }, navigationIcon = {
            IconButton(onClick = { navigator.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
        ) {
            when (characterState) {
                GetCharacterState.Idle -> {}
                GetCharacterState.Loading -> LoadingComponent(modifier = Modifier.fillMaxSize())
                is GetCharacterState.Success -> {
                    val characterData = (characterState as GetCharacterState.Success).data
                    characterData?.let { character ->
                        val placeholder = painterResource(id = R.drawable.default_character_image)

                        AsyncImage(
                            model = character.thumbnail,
                            contentDescription = null,
                            placeholder = placeholder,
                            error = placeholder,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                        )

                        if (character.description.isNotEmpty()) {
                            VerticalSpace(16.dp)
                            Text(
                                text = stringResource(id = R.string.description_label),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            VerticalSpace()
                            Text(
                                text = character.description,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        if (comics.itemCount > 0) {
                            VerticalSpace(16.dp)
                            Text(
                                text = stringResource(id = R.string.comics_label),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            VerticalSpace()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(comics) { item ->
                                    item?.let { comic ->
                                        ComicListItem(comic = comic)
                                    }
                                }

                                comics.apply {
                                    when {
                                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                            item {
                                                LoadingComponent(
                                                    modifier = Modifier
                                                        .width(100.dp)
                                                        .height(150.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (events.itemCount > 0) {
                            VerticalSpace(16.dp)
                            Text(
                                text = stringResource(id = R.string.events_label),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            VerticalSpace()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(events) { item ->
                                    item?.let { event ->
                                        EventListItem(event = event)
                                    }
                                }

                                events.apply {
                                    when {
                                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                            item {
                                                LoadingComponent(
                                                    modifier = Modifier
                                                        .width(100.dp)
                                                        .height(150.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (series.itemCount > 0) {
                            VerticalSpace(16.dp)
                            Text(
                                text = stringResource(id = R.string.series_label),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            VerticalSpace()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(series) { item ->
                                    item?.let { seriesItem ->
                                        SeriesListItem(series = seriesItem)
                                    }
                                }

                                series.apply {
                                    when {
                                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                            item {
                                                LoadingComponent(
                                                    modifier = Modifier
                                                        .width(100.dp)
                                                        .height(150.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (stories.itemCount > 0) {
                            VerticalSpace(16.dp)
                            Text(
                                text = stringResource(id = R.string.stories_label),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            VerticalSpace()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(stories) { item ->
                                    item?.let { story ->
                                        StoryListItem(story = story)
                                    }
                                }

                                stories.apply {
                                    when {
                                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                            item {
                                                LoadingComponent(
                                                    modifier = Modifier
                                                        .width(100.dp)
                                                        .height(150.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}