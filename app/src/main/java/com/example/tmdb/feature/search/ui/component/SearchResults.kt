package com.example.tmdb.feature.search.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tmdb.R
import com.example.tmdb.core.ui.component.PosterWithTotalVote
import com.example.tmdb.core.ui.component.TextIcon
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.search.network.json.SearchResultElement
import java.util.Locale

@Composable
fun SearchResults(
    searchResult: List<SearchResultElement>,
    getMovieGenres: (List<Int>) -> String,
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
    getMovieGenres: (List<Int>) -> String
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
            if (searchElement.releaseDate.split("-").size > 1) {
                TextIcon(
                    text = searchElement.releaseDate.split("-")[0],
                    iconId = R.drawable.calender
                )
            }
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

        if (searchElement.genreIds.isNotEmpty()) {
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
                    text = genres,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TMDBTheme.typography.caption,
                    color = TMDBTheme.colors.gray
                )
            }
        }
    }
}