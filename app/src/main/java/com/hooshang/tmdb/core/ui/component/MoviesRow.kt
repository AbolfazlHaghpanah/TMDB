package com.hooshang.tmdb.core.ui.component

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
import com.hooshang.tmdb.core.ui.shimmer.fakeMovie
import com.hooshang.tmdb.core.ui.shimmer.ifShimmerActive
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import com.hooshang.tmdb.feature.home.ui.component.MovieCard
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieRow(
    title: String,
    movies: PersistentList<HomeMovieDomainModel>,
    onClick: (Int) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp)
                .ifShimmerActive(movies.isEmpty()),
            text = title,
            style = Theme.typography.subtitle1,
            color = Theme.colors.white
        )

        LazyRow(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = movies.ifEmpty { fakeMovie },
                key = { it.movieId }
            ) { movieWithGenreDatabaseWrapper ->
                MovieCard(
                    modifier = Modifier.animateItemPlacement(),
                    onClick = { onClick(movieWithGenreDatabaseWrapper.movieId) },
                    title = movieWithGenreDatabaseWrapper.title,
                    image = movieWithGenreDatabaseWrapper.posterPath,
                    genres = movieWithGenreDatabaseWrapper.genres,
                    vote = String.format("%.1f", movieWithGenreDatabaseWrapper.voteAverage),
                    isShimmer = movies.isEmpty()
                )
            }
        }
    }
}