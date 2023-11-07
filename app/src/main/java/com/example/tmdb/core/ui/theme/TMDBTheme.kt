package com.example.tmdb.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBColors
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBIcons
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBShapes
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBType

object TMDBTheme {
    val colors: TMDBColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: TMDBType
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val icons: TMDBIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val shapes: TMDBShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShape.current

}