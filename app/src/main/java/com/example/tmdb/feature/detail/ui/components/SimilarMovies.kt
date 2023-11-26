package com.example.tmdb.feature.detail.ui.components


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tmdb.R
import com.example.tmdb.core.ui.component.PosterWithTotalVote
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.detail.data.relation.DetailMovieWithAllRelations

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

                    PosterWithTotalVote(
                        movieEntity = similarMovie.similarMovie,
                        backGroundColor = TMDBTheme.colors.surface.copy(alpha = 0.32f),
                        alignment = Alignment.TopEnd
                    )

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
                    Text(
                        text = similarMovie.genres.joinToString(separator = "|") { it.genreName },
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