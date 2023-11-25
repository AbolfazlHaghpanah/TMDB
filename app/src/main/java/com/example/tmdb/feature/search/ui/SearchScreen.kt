package com.example.tmdb.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.ui.component.PosterWithTotalVote
import com.example.tmdb.core.ui.component.TextIcon
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.search.network.json.SearchResultElement
import com.example.tmdb.feature.search.ui.component.TopSearchSection
import com.example.tmdb.navigation.AppScreens
import java.util.Locale

@Composable
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
    val searchResult by searchViewModel.searchResult.collectAsState()
    val apiResult by searchViewModel.apiResult.collectAsState()

    LaunchedEffect(apiResult) {
        searchViewModel.showLastSnackBar()
    }

    SearchScreen(
        apiResult = apiResult,
        searchResult = searchResult,
        onSearch = { newSearchString ->
            searchViewModel.getSearchResults(newSearchString)
        },
        getMovieGenres = { genreIds ->
            searchViewModel.getRelatedGenres(genreIds)
        },
        onSearchElementClick = {
            navController.navigate(AppScreens.Detail.createRoute(it))
        }
    )
}

@Composable
private fun SearchScreen(
    apiResult: Result,
    searchResult: List<SearchResultElement>,
    onSearch: (String) -> Unit,
    getMovieGenres: (List<Int>) -> List<String>,
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
            onSearch = onSearch,
            onSearchChange = { searchString = it }
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

@Composable
private fun LoadingSection() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        CircularProgressIndicator(
            color = TMDBTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun SearchResults(
    searchResult: List<SearchResultElement>,
    getMovieGenres: (List<Int>) -> List<String>,
    onSearchElementClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(searchResult) { searchElement ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.clickable {
                    onSearchElementClick(searchElement.id)
                }
            ) {

                PosterWithTotalVote(
                    movieEntity = searchElement.toMovieEntity(),
                    backGroundColor = TMDBTheme.colors.surface.copy(alpha = 0.7f),
                    alignment = Alignment.TopStart
                )

                Spacer(modifier = Modifier.weight(1f))

                MovieInfo(
                    searchElement = searchElement,
                    getMovieGenres = getMovieGenres
                )
            }
        }
    }
}

@Composable
private fun MovieInfo(
    searchElement: SearchResultElement,
    getMovieGenres: (List<Int>) -> List<String>
) {

    val genres = getMovieGenres(searchElement.genreIds)

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = searchElement.title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white,
            modifier = Modifier.widthIn(100.dp, 200.dp),
            maxLines = 1
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            TextIcon(
                text = searchElement.releaseDate.split("-")[0],
                iconId = R.drawable.calender
            )
            Box(
                Modifier.border(1.dp, TMDBTheme.colors.primary)
            ) {
                Text(
                    text = searchElement.originalLanguage.uppercase(Locale.getDefault()),
                    style = TMDBTheme.typography.caption,
                    color = TMDBTheme.colors.primary,
                    modifier = Modifier
                        .width(24.dp)
                        .padding(vertical = 6.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.film),
                contentDescription = null,
                tint = TMDBTheme.colors.gray
            )

            Text(
                text = genres.joinToString(separator = "  |  ") { it },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TMDBTheme.typography.caption,
                color = TMDBTheme.colors.gray
            )
        }
    }
}

@Composable
private fun NoSearchResultSection() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(2.1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.noresult),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.movie_search_error1),
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.whiteGray,
            minLines = 2,
            modifier = Modifier
                .width(168.dp)
                .padding(top = 16.dp, bottom = 20.dp)
        )
        Text(
            text = stringResource(R.string.find_movie_by_title),
            style = TMDBTheme.typography.caption,
            color = TMDBTheme.colors.gray
        )
    }
}