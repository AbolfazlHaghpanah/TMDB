package com.hooshang.tmdb.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBColors
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBShapes
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBType

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