package com.hooshang.tmdb.feature.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.search.ui.component.LoadingSection
import com.hooshang.tmdb.feature.search.ui.component.NoSearchResultSection
import com.hooshang.tmdb.feature.search.ui.component.SearchResults
import com.hooshang.tmdb.feature.search.ui.component.SearchTextFieldSection
import com.hooshang.tmdb.feature.search.ui.contracts.SearchAction
import com.hooshang.tmdb.feature.search.ui.contracts.SearchState
import com.hooshang.tmdb.navigation.AppScreens

@Composable
@NonRestartableComposable
fun SearchScreen(
    navController: NavController
) {
    SearchScreen(navController = navController, searchViewModel = hiltViewModel())
}

@Composable
private fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val searchState by searchViewModel.state.collectAsStateWithLifecycle()

    val onAction: (action: SearchAction) -> Unit = { action ->
        when (action) {
            is SearchAction.NavigateToDetail -> {
                navController.navigate(
                    AppScreens.Detail.createRoute(
                        action.id
                    )
                )
            }

            else -> {
                searchViewModel.onAction(action)
            }
        }
    }

    SearchScreen(
        searchState = searchState,
        onAction = onAction
    )
}

@Composable
private fun SearchScreen(
    searchState: SearchState,
    onAction: (SearchAction) -> Unit
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchTextFieldSection(
            searchString = searchText,
            onSearchChange = {
                onAction(SearchAction.OnSearch(input = it))
                searchText = it
            }
        )

        if (searchState.isLoading) {
            LoadingSection()
        } else {
            if (searchState.searchResults.isNotEmpty()) {
                SearchResults(
                    searchResult = searchState.searchResults,
                    onClick = { onAction(SearchAction.NavigateToDetail(it)) }
                )
            } else if (searchText.isNotEmpty() && searchState.isError.not()) {
                NoSearchResultSection(
                    image = ImageVector.vectorResource(TMDBTheme.icons.noResult),
                    title = stringResource(R.string.desc_movie_search_error),
                    description = stringResource(R.string.desc_find_movie_by_title)
                )
            }
        }
    }
}