package com.hooshang.tmdb.core.ui.theme.designsystem

import androidx.compose.material.Colors
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class Color(
    val primary: Color = Primary,
    val secondary: Color = Secondary,
    val background: Color = Background,
    val surface: Color = Surface,
    val error: Color = Error,
    val white: Color = White,
    val gray: Color = Gray,
    val whiteGray: Color = WhiteGray
) {
    fun toColor() = Colors(
        primary = primary,
        secondary = secondary,
        background = background,
        surface = surface,
        error = error,
        isLight = true,
        onBackground = white,
        onError = error,
        primaryVariant = whiteGray,
        onPrimary = white,
        onSecondary = white,
        onSurface = white,
        secondaryVariant = whiteGray
    )
}

val LocalColors = staticCompositionLocalOf {
    Color()
}