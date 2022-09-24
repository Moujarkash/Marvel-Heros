package com.mod.marvelcomic.application.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(dp: Dp = 8.dp) {
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun HorizontalSpace(dp: Dp = 8.dp) {
    Spacer(modifier = Modifier.width(dp))
}