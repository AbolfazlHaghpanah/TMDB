package com.hooshang.tmdb.feature.detail.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.hooshang.tmdb.core.ui.component.MovieCard
import com.hooshang.tmdb.core.ui.shimmer.ifShimmerActive
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.detail.domain.model.SimilarMovieDomainModel
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimilarMoviesSection(
    title: String,
    movies: PersistentList<SimilarMovieDomainModel>,
    onClick: (Int) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp)
                .ifShimmerActive(movies.isEmpty()),
            text = title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white
        )

        LazyRow(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                MovieCard(
                    modifier = Modifier.animateItemPlacement(),
                    onClick = { onClick(movie.id) },
                    title = movie.title,
                    image = movie.posterPath ?: "",
                    genres = movie.genres,
                    vote = String.format("%.1f", movie.voteAverage),
                    isShimmer = movies.isEmpty()
                )
            }
        }
    }
}