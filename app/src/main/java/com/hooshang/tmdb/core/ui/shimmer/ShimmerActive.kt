package com.hooshang.tmdb.core.ui.shimmer

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

fun Modifier.ifShimmerActive(
    isActive: Boolean
): Modifier {
    return composed {
        if (isActive) {
            Modifier
                .clip(Theme.shapes.rounded)
                .shimmer(
                    rememberShimmer(
                        shimmerBounds = ShimmerBounds.View,
                        theme = shimmerThemeProvider.value
                    )
                )
        } else {
            Modifier
        }
    }
}