package com.hooshang.tmdb.feature.favorite.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun EmptyIcon() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            FolderIcon()

            Text(
                text = stringResource(R.string.desc_there_is_no_movie_yet),
                style = TMDBTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun FolderIcon() {
    Image(
        painter = painterResource(id = TMDBTheme.icons.folder),
        contentDescription = null
    )
}