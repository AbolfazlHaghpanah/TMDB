package com.example.tmdb.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun BoxScope.VoteIcon(
    modifier: Modifier = Modifier,
    vote: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .zIndex(1f)
            .padding(8.dp)
            .then(modifier)
            .align(Alignment.TopEnd)
            .clip(TMDBTheme.shapes.small)
            .background(TMDBTheme.colors.surface.copy(alpha = 0.7f))
            .padding(8.dp, 4.dp)
    ) {
        VoteIcon()

        Text(
            text = vote,
            style = TMDBTheme.typography.body2,
            color = TMDBTheme.colors.secondary
        )
    }
}

@Composable
private fun VoteIcon() {
    Icon(
        modifier = Modifier
            .size(16.dp),
        painter = painterResource(id = TMDBTheme.icons.star),
        contentDescription = null,
        tint = TMDBTheme.colors.secondary
    )
}