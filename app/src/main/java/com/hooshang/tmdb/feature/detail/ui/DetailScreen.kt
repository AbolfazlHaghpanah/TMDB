package com.hooshang.tmdb.feature.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.component.MovieRow
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.feature.detail.ui.components.DetailTopWithGradient
import com.hooshang.tmdb.feature.detail.ui.components.OverviewContentWithCastAndCrew
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsAction
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsState
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
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
@NonRestartableComposable
private fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel
) {
    val movieDetail by detailViewModel.state.collectAsStateWithLifecycle()

    val onAction: (DetailsAction) -> Unit = remember {
        { action ->
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
    }

    DetailScreen(
        detailsState = movieDetail,
        onAction = onAction
    )
}

@Composable
private fun DetailScreen(
    detailsState: DetailsState,
    onAction: (DetailsAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        contentColor = Theme.colors.background.copy(alpha = 1f),
        backgroundColor = Theme.colors.background.copy(alpha = 1f),
        modifier = Modifier.background(Theme.colors.background)
    ) { paddingValues ->

        if (detailsState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        } else if (detailsState.movie.id != -1 && detailsState.movie.genres.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .background(Theme.colors.background)
                    .navigationBarsPadding()
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {

                DetailTopWithGradient(
                    detailsState = detailsState.movie,
                    onBackArrowClick = { onAction(DetailsAction.Back) },
                    onFavoriteIconClick = {
                        onAction(
                            if (detailsState.movie.isFavorite) DetailsAction.RemoveFromFavorite
                            else DetailsAction.AddToFavorite
                        )
                    }
                )

                OverviewContentWithCastAndCrew(detailsState.movie)

                if (detailsState.movie.similar.isNotEmpty()) {
                    MovieRow(
                        onClick = { onAction(DetailsAction.NavigateToDetails(it)) },
                        title = stringResource(R.string.label_similar_movies),
                        movies = detailsState.movie.similar.map {
                            HomeMovieDomainModel(
                                title = it.title,
                                voteAverage = it.voteAverage.toDouble(),
                                posterPath = it.posterPath ?: "",
                                movieId = it.id,
                                genres = it.genreIds,
                                backdropPath = it.posterPath ?: ""
                            )
                        }.toPersistentList()
                    )
                }
            }
        }
    }
}
