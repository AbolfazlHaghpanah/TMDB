package com.example.tmdb.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdb.R
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.core.utils.imageUrl
import java.math.RoundingMode

@Composable
fun PosterWithTotalVote(
    movieEntity: MovieEntity,
    backGroundColor: Color,
    alignment: Alignment
) {
    Box {
        SimilarMovieImageWrapper(movieEntity.posterPath)
        Row(
            modifier = Modifier
                .align(alignment)
                .padding(8.dp)
                .clip(TMDBTheme.shapes.small)
                .background(color = backGroundColor)
        ) {
            val roundedVote = movieEntity.voteAverage.toBigDecimal()
                .setScale(1, RoundingMode.FLOOR).toDouble()
            TextIcon(
                text = roundedVote.toString(),
                iconId = R.drawable.star,
                iconColor = TMDBTheme.colors.secondary,
                textColor = TMDBTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun SimilarMovieImageWrapper(similarMoviePosterPath: String) {
    AsyncImage(
        model = "$imageUrl${similarMoviePosterPath}",
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .clip(
                TMDBTheme.shapes.medium.copy(
                    bottomEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)
                )
            )
            .width(135.dp)
            .aspectRatio(0.7f),
        error = painterResource(id = R.drawable.videoimageerror)
    )
}