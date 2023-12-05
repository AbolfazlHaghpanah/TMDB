package com.hooshang.tmdb.core.ui.theme.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

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