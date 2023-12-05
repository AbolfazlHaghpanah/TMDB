package com.hooshang.tmdb.core.ui.theme.designsystem

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class TMDBShapes(
    val verySmall: CornerBasedShape = RoundedCornerShape(4.dp),
    val small: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(12.dp),
    val large: CornerBasedShape = RoundedCornerShape(16.dp),
    val veryLarge: CornerBasedShape = RoundedCornerShape(24.dp),
    val huge: CornerBasedShape = RoundedCornerShape(30.dp),
    val rounded: CornerBasedShape = RoundedCornerShape(50)
) {
    @Composable
    fun toShapes(): Shapes {
        return MaterialTheme.shapes.copy(
            small = small,
            medium = medium,
            large = large
        )
    }
}