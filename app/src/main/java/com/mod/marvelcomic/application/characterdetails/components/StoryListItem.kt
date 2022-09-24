package com.mod.marvelcomic.application.characterdetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mod.marvelcomic.R
import com.mod.marvelcomic.domain.models.Story

@Composable
fun StoryListItem(
    story: Story,
    modifier: Modifier = Modifier,
) {
    val placeholder = painterResource(id = R.drawable.default_character_image)

    Card(
        modifier = modifier.width(300.dp), elevation = 4.dp
    ) {
        Column {
            AsyncImage(
                model = story.thumbnail,
                contentDescription = null,
                placeholder = placeholder,
                error = placeholder,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(300.dp)
                    .height(150.dp)
            )
            Text(
                text = story.title,
                modifier = Modifier.padding(8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}