package com.hooshang.tmdb.core.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.hooshang.tmdb.core.ui.theme.designsystem.Color
import com.hooshang.tmdb.core.ui.theme.designsystem.Shapes
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.core.ui.theme.designsystem.Type

@Composable
fun TMDBTheme(
    typography: Type = Theme.typography,
    colors: Color = Theme.colors,
    shapes: Shapes = Theme.shapes,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = typography.toTypography(),
        colors = colors.toColor(),
        content = content,
        shapes = shapes.toShapes()
    )
}