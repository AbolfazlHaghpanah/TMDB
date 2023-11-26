package com.example.tmdb.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun VoteIcon(
    modifier: Modifier = Modifier,
    vote: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .zIndex(1f)
            .then(modifier)

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
        imageVector = ImageVector.vectorResource(TMDBTheme.icons.star),
        contentDescription = null,
        tint = TMDBTheme.colors.secondary
    )
}