package com.hooshang.tmdb.core.ui.theme.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object Theme {
    val colors: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Type
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val icons: Icons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShape.current
}