package com.hooshang.tmdb.feature.home.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.component.MovieCard
import com.hooshang.tmdb.core.ui.shimmer.fakeMovie
import com.hooshang.tmdb.core.ui.shimmer.ifShimmerActive
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieRow(
    title: String,
    movies: PersistentList<HomeMovieDomainModel>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val firstVisibleItem by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }
    val lastVisibleItem by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp)
                .ifShimmerActive(movies.isEmpty()),
            text = title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white
        )

        LazyRow(
            state = lazyListState,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = movies.ifEmpty { fakeMovie },
                key = { _, movie ->
                    if (movies.isNotEmpty()) movie.movieId else movie.title
                }
            ) { index, movieWithGenreDatabaseWrapper ->
                val animatedScale by animateFloatAsState(
                    if (index in firstVisibleItem..lastVisibleItem) 1f else 0.7f,
                    label = "animated_scale"
                )

                MovieCard(
                    modifier = Modifier
                        .animateItemPlacement()
                        .scale(animatedScale)
                        .alpha(animatedScale),
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