package com.example.tmdb.feature.search.ui

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
import com.example.tmdb.core.network.Result
import com.example.tmdb.feature.search.network.json.SearchResultElement
import com.example.tmdb.feature.search.ui.component.LoadingSection
import com.example.tmdb.feature.search.ui.component.NoSearchResultSection
import com.example.tmdb.feature.search.ui.component.SearchResults
import com.example.tmdb.feature.search.ui.component.TopSearchSection
import com.example.tmdb.navigation.AppScreens
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@NonRestartableComposable
@Composable
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
    val searchResult by searchViewModel.searchResult.collectAsStateWithLifecycle()
    val apiResult by searchViewModel.apiResult.collectAsStateWithLifecycle()
    LaunchedEffect(apiResult) {
        searchViewModel.showLastSnackBar()
    }


    SearchScreen(
        apiResult = apiResult,
        searchResult = searchResult.toPersistentList(),
        onSearch = remember {
            { newSearchString ->
                searchViewModel.getSearchResults(newSearchString)
            }
        },
        getMovieGenres = remember {
            { genreIds ->
                searchViewModel.getRelatedGenres(genreIds)
            }
        },
        onSearchElementClick = remember {
            {
                navController.navigate(AppScreens.Detail.createRoute(it))
            }
        }
    )
}

@Composable
private fun SearchScreen(
    apiResult: Result?,
    searchResult: PersistentList<SearchResultElement>,
    onSearch: (String) -> Unit,
    getMovieGenres: (List<Int>) -> String,
    onSearchElementClick: (Int) -> Unit
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
                onSearch(it)
                searchString = it
            }
        )

        if (apiResult == Result.Loading) {
            LoadingSection()
        } else {
            if (searchResult.isNotEmpty()) {
                SearchResults(searchResult, getMovieGenres, onSearchElementClick)
            } else if (searchString != "" && apiResult !is Result.Error) {
                NoSearchResultSection()
            }
        }
    }
}