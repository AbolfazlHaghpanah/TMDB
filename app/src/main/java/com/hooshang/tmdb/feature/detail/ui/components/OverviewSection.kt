package com.hooshang.tmdb.feature.detail.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun OverviewSection(
    overview: String
) {
    Text(
        modifier = Modifier
            .padding(bottom = 8.dp, top = 24.dp, start = 24.dp),
        text = stringResource(R.string.label_overview),
        color = TMDBTheme.colors.white,
        style = TMDBTheme.typography.subtitle1
    )

    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = overview,
        color = TMDBTheme.colors.whiteGray,
        style = TMDBTheme.typography.subtitle2,
    )
}