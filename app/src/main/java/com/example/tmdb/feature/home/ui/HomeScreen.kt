package com.example.tmdb.feature.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.home.data.movie.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.relation.PopularMovieWithGenre
import com.example.tmdb.feature.home.data.relation.TopMovieWithGenre
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreen(navController = navController, viewModel = hiltViewModel())
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsState()
    val topRated by viewModel.topMovies.collectAsState()
    val pagerState = rememberPagerState()

    HomeScreen(
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        topMovies = topRated,
        pagerState = pagerState
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    nowPlayingMovies: List<NowPlayingEntity>,
    popularMovies: List<PopularMovieWithGenre>,
    topMovies: List<TopMovieWithGenre>,
    pagerState: PagerState
) {
    Scaffold { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                HorizontalPager(
                    state = pagerState,
                    count = if (nowPlayingMovies.isEmpty()) 0 else 5,
                ) { page ->
                    Card(
                        shape = TMDBTheme.shapes.medium,
                        elevation = 10.dp
                    ) {
                        PagerItem(
                            nowPlayingMovies[page].title,
                            nowPlayingMovies[page].backdropPath,
                            nowPlayingMovies[page].releaseDate
                        )
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Most popular",
                    style = TMDBTheme.typography.subtitle1
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(popularMovies) {
                        MovieCard(
                            it.movie.title,
                            it.movie.posterPath,
                            it.genres.joinToString(separator = "|") { it.genre },
                            String.format("%.1f", it.movie.voteAverage)
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Top Rated",
                    style = TMDBTheme.typography.subtitle1
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(topMovies) {
                        MovieCard(
                            it.movie.title,
                            it.movie.posterPath,
                            it.genres.joinToString(separator = "|") { it.genre },
                            String.format("%.1f", it.movie.voteAverage)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieCard(

    title: String,
    image: String,
    dis: String,
    vote: String
) {
    Column(
        modifier = Modifier
            .padding(end = 14.dp)
            .clip(TMDBTheme.shapes.medium)
            .background(
                TMDBTheme.colors.surface,
                TMDBTheme.shapes.medium
            )
            .width(140.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .zIndex(1f)
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .clip(TMDBTheme.shapes.small)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp, 4.dp)

            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = TMDBTheme.icons.star),
                    contentDescription = null,
                    tint = TMDBTheme.colors.secondary
                )

                Text(
                    text = vote,
                    style = TMDBTheme.typography.body2,
                    color = TMDBTheme.colors.secondary
                )
            }
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = "https://tmdb-api.samentic.com/image/t/p/w500/$image",
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 4.dp),
            text = title,
            style = TMDBTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .basicMarquee(),
            text = dis,
            style = TMDBTheme.typography.overLine,
            maxLines = 1,
            color = TMDBTheme.colors.gray
        )
    }
}

@Composable
fun PagerItem(
    title: String,
    image: String,
    date: String
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(180.dp)
    ) {

        AsyncImage(
            modifier = Modifier
                .zIndex(-1f)
                .fillMaxSize(),
            model = "https://tmdb-api.samentic.com/image/t/p/w500/$image",
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .zIndex(0f)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Transparent, Color.Black),
                    )
                )
        )
        Column(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = TMDBTheme.typography.h6,
                color = TMDBTheme.colors.white
            )

            Text(
                text = date,
                style = TMDBTheme.typography.caption,
                color = TMDBTheme.colors.white
            )
        }
    }
}
