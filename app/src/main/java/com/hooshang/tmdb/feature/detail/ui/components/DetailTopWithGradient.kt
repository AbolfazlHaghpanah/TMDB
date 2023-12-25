package com.hooshang.tmdb.feature.detail.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.component.TextIcon
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.core.utils.imageUrl

@Composable
fun ForegroundImage(
    movieDetailPosterPath: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.1f)
            .padding(start = 85.dp, end = 85.dp)
            .clip(TMDBTheme.shapes.medium),
        model = "$imageUrl${movieDetailPosterPath}",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.videoimageerror)
    )
}

@Composable
fun BackgroundImage(movieDetailPosterPath: String) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            TMDBTheme.colors.background.copy(alpha = 0.57f),
            TMDBTheme.colors.background.copy(alpha = 1f)
        )
    )

    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient)
                }
            },
        model = "$imageUrl${movieDetailPosterPath}",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.videoimageerror)
    )
}

@Composable
fun MovieInfo(
    releaseDate: String,
    runtime: Int,
    genre: String,
    voteAverage: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        TextIcon(
            iconId = TMDBTheme.icons.calendar,
            text = releaseDate
        )

        if (runtime != 0) {
            Divider(
                color = TMDBTheme.colors.gray,
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .align(Alignment.CenterVertically)
            )

            TextIcon(
                iconId = TMDBTheme.icons.clock,
                text = "$runtime " + stringResource(R.string.minutes)
            )
        }

        if (genre.isNotEmpty()) {
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .align(Alignment.CenterVertically),
                color = TMDBTheme.colors.gray
            )

            TextIcon(
                iconId = TMDBTheme.icons.film,
                text = genre
            )
        }
    }

    if (voteAverage != "0.0") {
        TextIcon(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp),
            text = voteAverage,
            iconId = TMDBTheme.icons.star,
            iconColor = TMDBTheme.colors.secondary,
            textColor = TMDBTheme.colors.secondary
        )
    }
}