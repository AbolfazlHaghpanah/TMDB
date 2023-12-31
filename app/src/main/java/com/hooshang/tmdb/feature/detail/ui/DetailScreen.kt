package com.hooshang.tmdb.feature.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hooshang.tmdb.R
import com.hooshang.tmdb.feature.detail.ui.components.BackgroundImage
import com.hooshang.tmdb.feature.detail.ui.components.CastOrCrewSection
import com.hooshang.tmdb.feature.detail.ui.components.ForegroundImage
import com.hooshang.tmdb.feature.detail.ui.components.MovieInfo
import com.hooshang.tmdb.feature.detail.ui.components.OverviewSection
import com.hooshang.tmdb.feature.detail.ui.components.SimilarMoviesSection
import com.hooshang.tmdb.feature.detail.ui.components.TopBar
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsAction
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsState
import com.hooshang.tmdb.navigation.AppScreens
import kotlinx.collections.immutable.toPersistentList

@Composable
@NonRestartableComposable
fun DetailsScreen(navController: NavController) {
    DetailScreen(
        navController = navController,
        detailViewModel = hiltViewModel()
    )
}

@Composable
private fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel
) {
    val movieDetail by detailViewModel.state.collectAsStateWithLifecycle()

    val onAction: (DetailsAction) -> Unit = { action ->
        when (action) {

            is DetailsAction.NavigateToDetails -> {
                navController.navigate(AppScreens.Detail.createRoute(action.id))
            }

            is DetailsAction.Back -> {
                navController.navigateUp()
            }

            else -> {
                detailViewModel.onAction(action)
            }
        }
    }

    DetailScreen(
        detailsState = movieDetail,
        onAction = onAction,
    )
}

@Composable
private fun DetailScreen(
    detailsState: DetailsState,
    onAction: (DetailsAction) -> Unit
) {
    val scrollState = rememberScrollState()

    if (detailsState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            LinearProgressIndicator(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )
        }
    } else if (detailsState.movie.id != -1) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(scrollState)
        ) {
            Box {
                BackgroundImage(detailsState.movie.posterPath)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopBar(
                        title = detailsState.movie.title,
                        movieId = detailsState.movie.id,
                        externalIds = detailsState.movie.externalIds,
                        isFavorite = detailsState.movie.isFavorite,
                        onBackArrowClick = { onAction(DetailsAction.Back) },
                        onFavoriteIconClick = {
                            onAction(
                                if (detailsState.movie.isFavorite) {
                                    DetailsAction.RemoveFromFavorite
                                } else {
                                    DetailsAction.AddToFavorite
                                }
                            )
                        }
                    )

                    ForegroundImage(
                        modifier = Modifier.padding(top = 30.dp, bottom = 50.dp),
                        movieDetailPosterPath = detailsState.movie.posterPath
                    )

                    MovieInfo(
                        releaseDate = detailsState.movie.releaseDate.split("-")[0],
                        runtime = detailsState.movie.runtime,
                        genre = if (detailsState.movie.genres.isNotEmpty()) {
                            detailsState.movie.genres[0].second
                        } else {
                            ""
                        },
                        voteAverage = detailsState.movie.voteAverage.toString()
                    )
                }
            }

            OverviewSection(detailsState.movie.overview)

            if (detailsState.movie.credits.isNotEmpty()) {
                CastOrCrewSection(detailsState.movie.credits.toPersistentList())
            }

            if (detailsState.movie.similar.isNotEmpty()) {
                SimilarMoviesSection(
                    title = stringResource(R.string.label_similar_movies),
                    movies = detailsState.movie.similar.toPersistentList(),
                    onClick = { onAction(DetailsAction.NavigateToDetails(it)) }
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize())
    }

}
