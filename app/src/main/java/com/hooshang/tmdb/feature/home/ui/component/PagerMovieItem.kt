package com.hooshang.tmdb.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.hooshang.tmdb.core.ui.shimmer.ifShimmerActive
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.core.utils.imageUrl
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel

@Composable
fun PagerMovieItem(
    modifier: Modifier = Modifier,
    movie: HomeMovieDomainModel,
    isLoading: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = Theme.shapes.large,
        elevation = 24.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .zIndex(-1f)
                    .fillMaxSize(),
                model = imageUrl + movie.backdropPath,
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .zIndex(0f)
                    .fillMaxSize()
                    .then(
                        if (isLoading) {
                            Modifier.background(Theme.colors.surface)
                        } else {
                            Modifier.background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.8f)
                                    ),
                                )
                            )
                        }
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
                    modifier = Modifier.ifShimmerActive(isLoading),
                    text = movie.title,
                    style = Theme.typography.subtitle1,
                    color = Theme.colors.white
                )

                Text(
                    modifier = Modifier.ifShimmerActive(isLoading),
                    text = movie.releaseDate,
                    style = Theme.typography.caption,
                    color = Theme.colors.white
                )
            }
        }
    }
}
