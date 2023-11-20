package com.example.tmdb.feature.detail.ui.components


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdb.R
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.core.utils.imageUrl
import com.example.tmdb.feature.detail.data.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.data.SimilarMovieWithGenre
import com.example.tmdb.feature.detail.ui.common.RowWithIconAndText
import java.math.RoundingMode

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun SimilarMovies(
    movieDetail: DetailMovieWithAllRelations,
    onSimilarClick: (Int) -> Unit
) {
    if (movieDetail.similarMovies.isNotEmpty()) {
        Text(
            text = stringResource(R.string.similar_movies),
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white,
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 24.dp
                )
        )

        LazyRow(
            contentPadding = PaddingValues(
                start = 24.dp,
                top = 16.dp
            )
        ) {
            items(movieDetail.similarMovies) { similarMovie ->
                Column(
                    modifier = Modifier.clickable {
                        onSimilarClick(similarMovie.similarMovie.id)
                    }
                ) {

                    PosterWithTotalVote(similarMovie)

                    Text(
                        text = similarMovie.similarMovie.title,
                        style = TMDBTheme.typography.body1,
                        color = TMDBTheme.colors.white,
                        modifier = Modifier
                            .widthIn(60.dp, 135.dp)
                            .padding(horizontal = 8.dp)
                            .padding(top = 12.dp, bottom = 4.dp)
                            .basicMarquee()
                    )
                    var genresString = ""
                    for (elem in similarMovie.genres) {
                        genresString += elem.genreName
                        if (elem != similarMovie.genres.last()) genresString += "|"
                    }
                    Text(
                        text = genresString,
                        style = TMDBTheme.typography.overLine,
                        color = TMDBTheme.colors.gray,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(135.dp),
                        maxLines = 2
                    )
                }
            }
        }
    }
}

@Composable
private fun PosterWithTotalVote(similarMovie: SimilarMovieWithGenre) {
    Box {
        AsyncImage(
            model = "$imageUrl${similarMovie.similarMovie.posterPath}",
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(
                    TMDBTheme.shapes.medium.copy(
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
                .width(135.dp)
                .aspectRatio(0.7f),
            error = painterResource(id = R.drawable.videoimageerror)
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clip(TMDBTheme.shapes.small)
                .background(TMDBTheme.colors.surface.copy(alpha = 0.7f))
        ) {
            val roundedVote = similarMovie.similarMovie.voteAverage.toBigDecimal()
                .setScale(1, RoundingMode.FLOOR).toDouble()
            RowWithIconAndText(
                text = roundedVote.toString(),
                iconId = R.drawable.star,
                iconColor = TMDBTheme.colors.secondary,
                textColor = TMDBTheme.colors.secondary
            )
        }
    }
}
