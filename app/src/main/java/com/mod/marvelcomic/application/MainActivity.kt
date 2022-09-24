package com.mod.marvelcomic.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mod.marvelcomic.application.comiccharacters.NavGraphs
import com.mod.marvelcomic.application.theme.MarvelHerosTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHerosTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}