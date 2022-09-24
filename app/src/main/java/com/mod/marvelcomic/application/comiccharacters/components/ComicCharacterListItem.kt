package com.mod.marvelcomic.application.comiccharacters.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mod.marvelcomic.R
import com.mod.marvelcomic.domain.models.ComicCharacter

@Composable
fun ComicCharacterListItem(
    comicCharacter: ComicCharacter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val placeholder = painterResource(id = R.drawable.default_character_image)

    Card(modifier = modifier
        .fillMaxWidth(), elevation = 4.dp) {
        Column {
            AsyncImage(
                model = comicCharacter.thumbnail,
                contentDescription = null,
                placeholder = placeholder,
                error = placeholder,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = comicCharacter.name, modifier = Modifier.padding(8.dp))
                    if (comicCharacter.description.isNotEmpty())
                        Text(text = comicCharacter.description, modifier = Modifier.padding(8.dp), maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
                IconButton(onClick = onClick) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = MaterialTheme.colors.primary)
                }
            }
        }
    }
}