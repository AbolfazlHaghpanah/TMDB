package com.hooshang.tmdb.feature.favorite.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.favorite.ui.component.EmptyIcon
import com.hooshang.tmdb.feature.favorite.ui.component.MovieItems
import com.hooshang.tmdb.feature.favorite.ui.contracts.FavoriteActions
import com.hooshang.tmdb.feature.favorite.ui.contracts.FavoriteState
import com.hooshang.tmdb.navigation.AppScreens

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
    val favoriteState by viewModel.state.collectAsStateWithLifecycle()

    val onAction: (FavoriteActions) -> Unit = remember {
        { actions ->
            when (actions) {
                is FavoriteActions.OpenBottomSheet -> {
                    navController.navigate(AppScreens.BottomSheet.createRoute(actions.id))
                }

                is FavoriteActions.NavigateToDetails -> {
                    navController.navigate(AppScreens.Detail.createRoute(actions.id))
                }
            }
        }
    }

    FavoriteScreen(
        favoriteState = favoriteState,
        onAction = onAction
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FavoriteScreen(
    favoriteState: FavoriteState,
    onAction: (FavoriteActions) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                backgroundColor = TMDBTheme.colors.background
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.label_favorites),
                    style = TMDBTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    color = TMDBTheme.colors.white
                )
            }
        }
    ) { paddingValues ->

        if (favoriteState.movies.isEmpty()) {
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
                    items = favoriteState.movies,
                    key = { it.id }
                ) { movie ->
                    MovieItems(
                        modifier = Modifier
                            .animateItemPlacement()
                            .clickable { onAction(FavoriteActions.NavigateToDetails(movie.id)) },
                        movie = movie,
                        onDelete = {
                            onAction(FavoriteActions.OpenBottomSheet(movie.id))
                        }
                    )
                }
            }
        }
    }
}
