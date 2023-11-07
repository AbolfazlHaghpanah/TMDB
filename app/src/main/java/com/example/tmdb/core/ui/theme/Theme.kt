package com.example.tmdb.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBColors
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBShapes
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBType

@Composable
fun TMDBTheme(
    typography: TMDBType = TMDBTheme.typography,
    colors: TMDBColors = TMDBTheme.colors,
    shapes: TMDBShapes = TMDBTheme.shapes,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        typography = typography.toTypography(),
        colors = colors.toColor(),
        content = content,
        shapes = shapes.toShapes()
    )
}