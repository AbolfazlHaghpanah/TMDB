package com.hooshang.tmdb.feature.search.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme

@Composable
fun NoSearchResultSection() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(2.1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(Theme.icons.noResult),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.desc_movie_search_error),
            style = Theme.typography.subtitle1,
            color = Theme.colors.whiteGray,
            minLines = 2,
            modifier = Modifier
                .width(168.dp)
                .padding(top = 16.dp, bottom = 20.dp)
        )
        Text(
            text = stringResource(R.string.desc_find_movie_by_title),
            style = Theme.typography.caption,
            color = Theme.colors.gray
        )
    }
}