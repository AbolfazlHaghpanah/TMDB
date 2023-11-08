package com.example.tmdb.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.tmdb.core.ui.theme.designsystem.TMDBColors
import com.example.tmdb.core.ui.theme.designsystem.TMDBShapes
import com.example.tmdb.core.ui.theme.designsystem.TMDBType

@Composable
fun TMDBTheme(
    typography: TMDBType = com.example.tmdb.core.ui.theme.designsystem.TMDBTheme.typography,
    colors: TMDBColors = com.example.tmdb.core.ui.theme.designsystem.TMDBTheme.colors,
    shapes: TMDBShapes = com.example.tmdb.core.ui.theme.designsystem.TMDBTheme.shapes,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        typography = typography.toTypography(),
        colors = colors.toColor(),
        content = content,
        shapes = shapes.toShapes()
    )
}