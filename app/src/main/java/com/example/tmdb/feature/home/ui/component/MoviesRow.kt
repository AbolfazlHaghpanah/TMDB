package com.example.tmdb.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tmdb.core.ui.shimmer.fakeMovie
import com.example.tmdb.core.ui.shimmer.ifShimmerActive
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper

@Composable
fun MovieRow(
    onClick: (Int) -> Unit,
    title: String,
    movies: List<MovieWithGenreDatabaseWrapper>
) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp)
                .ifShimmerActive(movies.isEmpty()),
            text = title,
            style = TMDBTheme.typography.subtitle1
        )

        LazyRow(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = movies.ifEmpty { fakeMovie }) {
                MovieCard(
                    onClick = { onClick(it.movie.movieId) },
                    title = it.movie.title,
                    image = it.movie.posterPath,
                    genres = it.genres.joinToString(separator = "|") { it.genreName },
                    vote = String.format("%.1f", it.movie.voteAverage),
                    isShimmer = movies.isEmpty()
                )
            }
        }
    }
}