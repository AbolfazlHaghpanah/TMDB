package com.hooshang.tmdb.feature.home.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TMDBPagerIndicator(
    pageCount: Int,
    selectedPage: Int,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { page ->
            val activeSize by animateDpAsState(
                targetValue = if (page == selectedPage) 24.dp else 10.dp,
                label = ""
            )
            val color by animateFloatAsState(
                targetValue = if (page == selectedPage) 1f else 0.5f,
                label = ""
            )

            Box(
                modifier = Modifier
                    .background(
                        color = if (isLoading) Color.DarkGray.copy(alpha = color) else TMDBTheme.colors.primary.copy(
                            alpha = color
                        ),
                        TMDBTheme.shapes.rounded
                    )
                    .width(activeSize)
                    .height(10.dp)
                    .then(modifier),
            )
        }
    }
}