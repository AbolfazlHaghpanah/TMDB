package com.example.tmdb.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBIcons
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBShapes
import com.example.tmdb.core.ui.theme.tmdbtheme.TMDBType
import com.example.tmdb.core.ui.theme.tmdbtheme.darkColors


val LocalColors = staticCompositionLocalOf {
    darkColors()
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