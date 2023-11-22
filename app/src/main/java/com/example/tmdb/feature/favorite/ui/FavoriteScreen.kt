package com.example.tmdb.feature.favorite.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.favorite.ui.component.EmptyIcon
import com.example.tmdb.feature.favorite.ui.component.MovieItems
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.navigation.AppScreens

@Composable
fun FavoriteScreen(
    navController: NavController
) {
    FavoriteScreen(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
private fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    val favoriteMovies by viewModel.favoriteMovieList.collectAsState()

    val onDeleteMovie: (Int) -> Unit = remember {
        {
            navController.navigate(AppScreens.BottomSheet.createRoute(it))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onTryAgain()
    }

    FavoriteScreen(
        movies = favoriteMovies,
        onDeleteMovie = onDeleteMovie
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun FavoriteScreen(
    movies: List<MovieWithGenreDatabaseWrapper>,
    onDeleteMovie: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = TMDBTheme.colors.background
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.favorites),
                    style = TMDBTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    color = TMDBTheme.colors.white
                )
            }
        }
    ) { paddingValues ->

        if (movies.isEmpty()) {

            EmptyIcon()

        } else {

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = movies,
                    key = { it.movie.movieId }
                ) { movie ->
                    MovieItems(
                        modifier = Modifier
                            .animateItemPlacement(),
                        movie = movie,
                        onDelete = {
                            onDeleteMovie(movie.movie.movieId)
                        }
                    )
                }
            }
        }
    }
}
