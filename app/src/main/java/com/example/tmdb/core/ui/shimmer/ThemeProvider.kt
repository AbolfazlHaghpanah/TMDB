package com.example.tmdb.core.ui.shimmer

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme

val shimmerThemeProvider = LocalShimmerTheme.provides(
    defaultShimmerTheme.copy(
        shaderColors = listOf(
            Color.DarkGray.copy(alpha = 0.4f),
            Color.DarkGray.copy(alpha = 0.8f),
            Color.DarkGray.copy(alpha = 0.4f),
        ),
        blendMode = BlendMode.Src
    )
)
