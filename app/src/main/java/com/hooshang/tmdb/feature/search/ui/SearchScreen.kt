package com.hooshang.tmdb.feature.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hooshang.tmdb.feature.search.ui.component.LoadingSection
import com.hooshang.tmdb.feature.search.ui.component.NoSearchResultSection
import com.hooshang.tmdb.feature.search.ui.component.SearchResults
import com.hooshang.tmdb.feature.search.ui.component.TopSearchSection
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
@NonRestartableComposable
private fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val searchState by searchViewModel.state.collectAsStateWithLifecycle()

    val onAction: (action: SearchAction) -> Unit = remember {
        { action ->
            when (action) {
                is SearchAction.NavigateToDetail -> navController.navigate(
                    AppScreens.Detail.createRoute(
                        action.id
                    )
                )

                else -> searchViewModel.onAction(action)
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

    var searchString by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .statusBarsPadding()
            .padding(vertical = 8.dp, horizontal = 24.dp)

    ) {
        TopSearchSection(
            searchString = searchString,
            onSearchChange = {
                onAction(SearchAction.OnSearch(input = it))
                searchString = it
            }
        )

        if (searchState.isLoading) {
            LoadingSection()
        } else {
            if (searchState.searchResults.isNotEmpty()) {
                SearchResults(searchState.searchResults, onAction)
            } else if (searchString != "" && !searchState.isError) {
                NoSearchResultSection()
            }
        }
    }
}