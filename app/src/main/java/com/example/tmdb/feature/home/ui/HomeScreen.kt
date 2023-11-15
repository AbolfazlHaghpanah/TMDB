package com.example.tmdb.feature.home.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.ui.shimmer.fakeMovie
import com.example.tmdb.core.ui.shimmer.ifShimmerActive
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.ui.component.MovieRow
import com.example.tmdb.feature.home.ui.component.PagerMovieItem
import com.example.tmdb.feature.home.ui.component.TMDBPagerIndicator
import com.example.tmdb.navigation.AppScreens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val onNavigation: (String) -> Unit = remember {
        { route ->
            navController.navigate(route) {
                launchSingleTop = true
            }
        }
    }

    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsState()
    val topRated by viewModel.topMovies.collectAsState()
    val pagerState = rememberPagerState()
    val nowPlayingResult by viewModel.nowPlayingResult.collectAsState()
    val popularMovieResult by viewModel.popularMovieResult.collectAsState()
    val topMovieResult by viewModel.topMovieResult.collectAsState()

    HomeScreen(
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        topMovies = topRated,
        pagerState = pagerState,
        nowPlayingResult = nowPlayingResult,
        popularMovieResult = popularMovieResult,
        topMovieResult = topMovieResult,
        onNavigation = onNavigation
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    nowPlayingMovies: List<MovieWithGenreDatabaseWrapper>,
    popularMovies: List<MovieWithGenreDatabaseWrapper>,
    topMovies: List<MovieWithGenreDatabaseWrapper>,
    pagerState: PagerState,
    nowPlayingResult: Result,
    popularMovieResult: Result,
    topMovieResult: Result,
    onNavigation: (String) -> Unit
) {

    Scaffold { scaffoldPadding ->

        LazyColumn(
            modifier = Modifier
                .systemBarsPadding()
                .padding(scaffoldPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            item {
                HorizontalPager(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .height(180.dp),
                    state = pagerState,
                    count = if (nowPlayingMovies.size > 5) 5 else fakeMovie.size,
                    itemSpacing = 12.dp,
                    contentPadding = PaddingValues(horizontal = 40.dp)
                ) { page ->

                    val pagerSize = animateDpAsState(
                        targetValue = if (page == pagerState.currentPage) 180.dp else 160.dp,
                        label = ""
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        PagerMovieItem(
                            isLoading = nowPlayingMovies.size <= 5,
                            modifier = Modifier
                                .clip(TMDBTheme.shapes.large)
                                .clickable {
                                    onNavigation(AppScreens.Detail.route)
                                }
                                .height(pagerSize.value),
                            movie = if (nowPlayingMovies.size >= 5) {
                                nowPlayingMovies[page].movie
                            } else {
                                fakeMovie[0].movie
                            }
                        )
                    }
                }
            }

            item {
                TMDBPagerIndicator(
                    modifier = Modifier
                        .ifShimmerActive(nowPlayingMovies.isEmpty()),
                    pageCount = pagerState.pageCount,
                    selectedPage = pagerState.currentPage
                )
            }

            item {
                MovieRow(
                    onClick = { onNavigation(AppScreens.Detail.route) },
                    title = stringResource(R.string.most_popular),
                    movies = popularMovies
                )
            }

            item {
                MovieRow(
                    onClick = { onNavigation(AppScreens.Detail.route) },
                    title = stringResource(R.string.top_rated),
                    movies = topMovies
                )
            }
        }
    }
}






