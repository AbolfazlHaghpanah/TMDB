package com.hooshang.tmdb.feature.favorite.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hooshang.tmdb.core.ui.component.TextIcon
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.core.utils.image_url
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel

@Composable
fun MovieItems(
    modifier: Modifier = Modifier,
    movie: FavoriteMovieDomainModel,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(TMDBTheme.shapes.large)
            .background(TMDBTheme.colors.surface, TMDBTheme.shapes.large)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(TMDBTheme.shapes.small)
                .fillMaxHeight()
                .width(130.dp),
            model = image_url + movie.backdropPath,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        InfoSection(
            title = movie.title,
            genres = movie.genres,
            vote = String.format("%.1f", movie.voteAverage),
            onDelete = onDelete
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun InfoSection(
    title: String,
    genres: String,
    vote: String,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier.basicMarquee(),
            text = title,
            style = TMDBTheme.typography.body1,
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(0.75f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 48.dp)
                        .fillMaxWidth(),
                    text = genres,
                    style = TMDBTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                TextIcon(
                    text = vote,
                    iconId = TMDBTheme.icons.star,
                    iconColor = TMDBTheme.colors.secondary,
                    textColor = TMDBTheme.colors.secondary
                )
            }

            IconButton(
                modifier = Modifier
                    .clip(TMDBTheme.shapes.rounded)
                    .weight(0.25f),
                onClick = onDelete
            ) {
                Icon(
                    modifier = Modifier
                        .size(26.dp),
                    painter = painterResource(id = TMDBTheme.icons.heart),
                    contentDescription = null,
                    tint = TMDBTheme.colors.error
                )
            }
        }
    }
}