package com.hooshang.tmdb.feature.favorite.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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

        ImageSection(image = movie.backdropPath)

        InfoSection(
            title = movie.title,
            genres = movie.genres,
            vote = String.format("%.1f", movie.voteAverage),
            onDelete = onDelete
        )
    }
}

@Composable
private fun ImageSection(
    image: String
) {
    AsyncImage(
        modifier = Modifier
            .clip(TMDBTheme.shapes.small)
            .fillMaxHeight()
            .width(130.dp),
        model = image_url + image,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun InfoSection(
    title: String,
    genres: String,
    vote: String,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .fillMaxSize()
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = TMDBTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(end = 48.dp),
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

        FavoriteIcon(onDelete = onDelete)
    }
}

@Composable
private fun BoxScope.FavoriteIcon(
    onDelete: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .clip(TMDBTheme.shapes.rounded)
            .align(Alignment.BottomEnd),
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