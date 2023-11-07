package com.example.tmdb.core.ui.theme.tmdbtheme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class TMDBShapes(
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
){
    @Composable
    fun toShapes():Shapes {
        return MaterialTheme.shapes.copy(
            small = small
        )
    }
}
