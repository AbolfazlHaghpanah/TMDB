package com.example.tmdb.feature.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.detail.data.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.ui.components.DetailTopWithGradient
import com.example.tmdb.feature.detail.ui.components.OverviewContentWithCastAndCrew
import com.example.tmdb.feature.detail.ui.components.SimilarMovies
import com.example.tmdb.navigation.AppScreens

@Composable
fun DetailsScreen(navController: NavController) {
    DetailScreen(
        navController = navController,
        detailViewModel = hiltViewModel()
    )
}

@Composable
fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel
) {

    val movieDetail = detailViewModel.movieDetail.collectAsState().value
    DetailScreen(
        movieDetail = movieDetail,
        onBackArrowClick = { navController.navigateUp() },
        onSimilarItemClick = { navController.navigate(AppScreens.Detail.createRoute(it)) }
    )

}

@Composable
fun DetailScreen(
    movieDetail: DetailMovieWithAllRelations?,
    onBackArrowClick: () -> Unit,
    onSimilarItemClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        contentColor = TMDBTheme.colors.background.copy(alpha = 1f),
        backgroundColor = TMDBTheme.colors.background.copy(alpha = 1f),
        modifier = Modifier.background(TMDBTheme.colors.background)
    ) { paddingValues ->
        movieDetail?.let { movieDetail ->
            Column(
                modifier = Modifier
                    .background(TMDBTheme.colors.background)
                    .navigationBarsPadding()
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {

                DetailTopWithGradient(movieDetail, onBackArrowClick)

                OverviewContentWithCastAndCrew(movieDetail)

                SimilarMovies(movieDetail, onSimilarItemClick)

            }
        }
    }
}
