package com.hooshang.tmdb.feature.search.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.component.TextIcon
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.core.utils.image_url
import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.ui.contracts.SearchAction
import kotlinx.collections.immutable.PersistentList
import java.math.RoundingMode
import java.util.Locale

@Composable
fun SearchResults(
    searchResult: PersistentList<SearchMovieWithGenreDomainModel>,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(searchResult) { movie ->
            SearchMovieItem(
                movie = movie,
                onClick = { onClick(movie.movieDomainModel.id) }
            )
        }
    }
}

@Composable
private fun SearchMovieItem(
    movie: SearchMovieWithGenreDomainModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PosterWithTotalVote(movie)

        Spacer(modifier = Modifier.weight(1f))

        MovieInfo(movie)
    }
}

@Composable
private fun MovieInfo(
    movie: SearchMovieWithGenreDomainModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = movie.movieDomainModel.title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white,
            modifier = Modifier.widthIn(100.dp, 200.dp),
            maxLines = 1
        )

        Row(
            modifier = Modifier.padding(top = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (movie.movieDomainModel.releaseDate.split("-").size > 1) {
                TextIcon(
                    text = movie.movieDomainModel.releaseDate.split("-")[0],
                    iconId = TMDBTheme.icons.calendar
                )
            }

            Text(
                modifier = Modifier
                    .border(1.dp, TMDBTheme.colors.primary, TMDBTheme.shapes.verySmall)
                    .padding(4.dp),
                text = movie.movieDomainModel.originalLanguage.uppercase(Locale.getDefault()),
                style = TMDBTheme.typography.caption,
                color = TMDBTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }

        if (movie.genres.isNotEmpty()) {
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(TMDBTheme.icons.film),
                    contentDescription = null,
                    tint = TMDBTheme.colors.gray
                )

                Text(
                    text = movie.genres,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TMDBTheme.typography.caption,
                    color = TMDBTheme.colors.gray
                )
            }
        }
    }
}

@Composable
private fun PosterWithTotalVote(
    movie: SearchMovieWithGenreDomainModel
) {
    Box {
        AsyncImage(
            modifier = Modifier
                .clip(TMDBTheme.shapes.medium)
                .width(112.dp)
                .aspectRatio(0.8f),
            model = "$imageUrl${movie.movieDomainModel.posterPath}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.videoimageerror)
        )

        TextIcon(
            modifier = Modifier
                .padding(8.dp)
                .clip(TMDBTheme.shapes.small)
                .background(TMDBTheme.colors.surface.copy(alpha = 0.7f))
                .padding(8.dp, 4.dp),
            text = String.format("%.1f", movie.movieDomainModel.voteAverage),
            iconId = TMDBTheme.icons.star,
            iconColor = TMDBTheme.colors.secondary,
            textColor = TMDBTheme.colors.secondary
        )
    }
}