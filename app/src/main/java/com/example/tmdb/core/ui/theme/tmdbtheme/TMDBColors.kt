package com.example.tmdb.core.ui.theme.tmdbtheme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class TMDBColors(
    primary: Color = Color.Blue,
    text: Color = Color.Red,
    background: Color = Color.White
) {
    var primary by mutableStateOf(primary)
        private set

    var text by mutableStateOf(text)
        private set

    var background by mutableStateOf(background)
        private set
    @Composable
    fun toColor(): Colors {
        return MaterialTheme.colors.copy(
            primary = primary
        )
    }
}