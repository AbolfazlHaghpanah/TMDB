package com.example.tmdb.feature.home.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.movie.entity.NowPlayingEntity
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
            navController.navigate(route)
        }
    }

    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsState()
    val topRated by viewModel.topMovies.collectAsState()
    val pagerState = rememberPagerState()
    val result by viewModel.result.collectAsState()

    HomeScreen(
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        topMovies = topRated,
        pagerState = pagerState,
        result = result,
        onNavigation = onNavigation
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    nowPlayingMovies: List<NowPlayingEntity>,
    popularMovies: List<MovieWithGenreDatabaseWrapper>,
    topMovies: List<MovieWithGenreDatabaseWrapper>,
    pagerState: PagerState,
    result: Result,
    onNavigation: (String) -> Unit
) {
    Scaffold { scaffoldPadding ->

        if (result == Result.Loading) {

            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 6.dp,
                )
            }
        } else {

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
                        count = if (nowPlayingMovies.isEmpty()) 0 else 5,
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
                                modifier = Modifier
                                    .clip(TMDBTheme.shapes.large)
                                    .clickable {
                                        onNavigation(AppScreens.Detail.route)
                                    }
                                    .height(pagerSize.value),
                                title = nowPlayingMovies[page].title,
                                image = nowPlayingMovies[page].backdropPath,
                                date = nowPlayingMovies[page].releaseDate
                            )
                        }
                    }
                }

                item {
                    TMDBPagerIndicator(
                        modifier = Modifier,
                        pageCount = pagerState.pageCount,
                        selectedPage = pagerState.currentPage
                    )
                }

                item {
                    MovieRow(
                        onClick = { onNavigation(AppScreens.Detail.route) },
                        title = "Most Popular",
                        movies = popularMovies
                    )
                }

                item {
                    MovieRow(
                        onClick = { onNavigation(AppScreens.Detail.route) },
                        title = "Top Rated",
                        movies = topMovies
                    )
                }
            }
        }
    }
}





