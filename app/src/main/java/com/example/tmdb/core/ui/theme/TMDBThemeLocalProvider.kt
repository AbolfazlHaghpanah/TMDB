package com.example.tmdb.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBColors
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBIcons
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBShapes
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBType


val LocalColors = staticCompositionLocalOf {
    TMDBColors()
}

val LocalTypography = staticCompositionLocalOf {
    TMDBType()
}

val LocalIcons = staticCompositionLocalOf {
    TMDBIcons()
}

val LocalShape = staticCompositionLocalOf {
    TMDBShapes()
}