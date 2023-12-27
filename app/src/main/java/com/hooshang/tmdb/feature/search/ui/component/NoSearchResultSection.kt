package com.hooshang.tmdb.feature.search.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun NoSearchResultSection(
    image : ImageVector,
    title : String,
    description : String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = image,
            contentDescription = null
        )

        Text(
            text = stringResource(R.string.desc_movie_search_error),
            modifier = Modifier
                .width(168.dp)
                .padding(top = 16.dp, bottom = 20.dp),
            text = title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.whiteGray,
            minLines = 2
        )

        Text(
            text = stringResource(R.string.desc_find_movie_by_title),
            text = description,
            style = TMDBTheme.typography.caption,
            color = TMDBTheme.colors.gray
        )
    }
}