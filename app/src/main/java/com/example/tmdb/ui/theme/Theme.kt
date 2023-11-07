package com.example.tmdb.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColor = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80,
)

@Composable
fun TMDBTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = DarkColor,
        typography = Typography,
        content = content
    )
}