package com.example.tmdb.feature.detail.ui

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.core.ui.component.MovieRow
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.detail.domain.model.MovieDetail
import com.example.tmdb.feature.detail.ui.components.DetailTopWithGradient
import com.example.tmdb.feature.detail.ui.components.OverviewContentWithCastAndCrew
import com.example.tmdb.feature.home.domain.model.HomeMovieDomainModel
import com.example.tmdb.navigation.AppScreens
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
    val movieDetail by detailViewModel.movieDetail.collectAsStateWithLifecycle()

    val onFavoriteIconClick = remember {
        {
            if (movieDetail?.isFavorite == false) detailViewModel.addToFavorite()
            else detailViewModel.removeFromFavorite()
        }
    }

    val isLoading by detailViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detailViewModel.showLastSnackBar()
    }

    DetailScreen(
        movieDetail = movieDetail,
        isLoading = isLoading,
        onBackArrowClick = remember { { navController.navigateUp() } },
        onSimilarItemClick = remember { { navController.navigate(AppScreens.Detail.createRoute(it)) } },
        onFavoriteIconClick = onFavoriteIconClick
    )

}

@Composable
private fun DetailScreen(
    movieDetail: MovieDetail?,
    isLoading: Boolean,
    onBackArrowClick: () -> Unit,
    onSimilarItemClick: (Int) -> Unit,
    onFavoriteIconClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        contentColor = TMDBTheme.colors.background.copy(alpha = 1f),
        backgroundColor = TMDBTheme.colors.background.copy(alpha = 1f),
        modifier = Modifier.background(TMDBTheme.colors.background)
    ) { paddingValues ->

        if (isLoading && movieDetail == null) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            movieDetail?.let { movieDetail ->
                Column(
                    modifier = Modifier
                        .background(TMDBTheme.colors.background)
                        .navigationBarsPadding()
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                ) {

                    DetailTopWithGradient(movieDetail, onBackArrowClick, onFavoriteIconClick)

                    OverviewContentWithCastAndCrew(movieDetail)

                    if (movieDetail.similar.isNotEmpty()) {
                        Log.d("test", "awdsnsdfa")
                        MovieRow(
                            onClick = onSimilarItemClick,
                            title = stringResource(R.string.similar_movies),
                            movies = movieDetail.similar.map {
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
}