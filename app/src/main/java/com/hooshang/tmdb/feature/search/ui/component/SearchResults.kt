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
import com.hooshang.tmdb.core.utils.imageUrl
import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.ui.contracts.SearchAction
import kotlinx.collections.immutable.PersistentList
import java.math.RoundingMode
import java.util.Locale

@Composable
fun SearchResults(
    searchResult: PersistentList<SearchMovieWithGenreDomainModel>,
    onSearchElementClick: (SearchAction) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(searchResult) { searchElement ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.clickable {
                    onSearchElementClick(SearchAction.NavigateToDetail(searchElement.movieDomainModel.id))
                }
            ) {

                PosterWithTotalVote(movie = searchElement)

                Spacer(modifier = Modifier.weight(1f))

                MovieInfo(
                    searchElement = searchElement,
                )
            }
        }
    }
}

@Composable
private fun MovieInfo(
    searchElement: SearchMovieWithGenreDomainModel,
) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = searchElement.movieDomainModel.title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white,
            modifier = Modifier.widthIn(100.dp, 200.dp),
            maxLines = 1
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            if (searchElement.movieDomainModel.releaseDate.split("-").size > 1) {
                TextIcon(
                    text = searchElement.movieDomainModel.releaseDate.split("-")[0],
                    iconId = TMDBTheme.icons.calendar
                )
            }
            Box(
                Modifier.border(1.dp, TMDBTheme.colors.primary)
            ) {
                Text(
                    text = searchElement.movieDomainModel.originalLanguage.uppercase(Locale.getDefault()),
                    style = TMDBTheme.typography.caption,
                    color = TMDBTheme.colors.primary,
                    modifier = Modifier
                        .width(24.dp)
                        .padding(vertical = 6.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        if (searchElement.genres.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(TMDBTheme.icons.film),
                    contentDescription = null,
                    tint = TMDBTheme.colors.gray
                )

                Text(
                    text = searchElement.genres,
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
    val roundedVote = movie.movieDomainModel.voteAverage.toBigDecimal()
        .setScale(1, RoundingMode.FLOOR).toDouble()

    Box {
        SimilarMovieImageWrapper(movie.movieDomainModel.posterPath)

        TextIcon(
            modifier = Modifier
                .padding(8.dp)
                .clip(TMDBTheme.shapes.small)
                .background(TMDBTheme.colors.surface.copy(alpha = 0.7f))
                .padding(8.dp, 4.dp),
            text = roundedVote.toString(),
            iconId = TMDBTheme.icons.star,
            iconColor = TMDBTheme.colors.secondary,
            textColor = TMDBTheme.colors.secondary
        )
    }
}

@Composable
private fun SimilarMovieImageWrapper(similarMoviePosterPath: String) {
    AsyncImage(
        model = "$imageUrl${similarMoviePosterPath}",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(
                TMDBTheme.shapes.medium
            )
            .width(112.dp)
            .aspectRatio(0.8f),
        error = painterResource(id = R.drawable.videoimageerror)
    )
}