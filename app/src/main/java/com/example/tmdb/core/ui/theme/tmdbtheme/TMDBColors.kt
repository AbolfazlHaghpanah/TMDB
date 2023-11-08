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
    primary: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    error: Color,
    onBackground: Color,
    onSurface: Color,
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var background by mutableStateOf(background)
        private set

    var surface by mutableStateOf(surface)
        private set

    var error by mutableStateOf(error)
        private set

    var onBackground by mutableStateOf(onBackground)
        private set

    var onSurface by mutableStateOf(onSurface)
        private set

    @Composable
    fun toColor(): Colors {
        return MaterialTheme.colors.copy(
            primary = primary,
            secondary = secondary,
            surface = surface,
            onBackground = onBackground,
            onSurface = onSurface,
            background = background,
            error = error,
            onPrimary = onBackground,
            onSecondary = onBackground
        )
    }
}

fun darkColors(
    primary: Color = Color(0xFF12CDD9),
    secondary: Color = Color(0xFFFF8700),
    background: Color = Color(0xFF1F1D2B),
    surface: Color = Color(0xFF252836),
    error: Color = Color(0xFFFB4141),
    onBackground: Color = Color(0xFFFFFFFF),
    onSurface: Color = Color(0xFF92929D),
): TMDBColors = TMDBColors(
    primary,
    secondary,
    background,
    surface,
    error,
    onBackground,
    onSurface,
)
