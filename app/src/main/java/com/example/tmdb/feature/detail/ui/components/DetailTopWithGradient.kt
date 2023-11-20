package com.example.tmdb.feature.detail.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.tmdb.R
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.example.tmdb.feature.detail.data.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.ui.common.RowWithIconAndText
import com.example.tmdb.feature.detail.ui.imageUrl
import java.math.RoundingMode

@Composable
fun DetailTopWithGradient(
    movieDetail: DetailMovieWithAllRelations,
    onBackArrowClick: () -> Unit
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            TMDBTheme.colors.background.copy(alpha = 0.57f),
            TMDBTheme.colors.background.copy(alpha = 1f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = "$imageUrl${movieDetail.movie.posterPath}",
            contentDescription = null,
            Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient)
                    }
                },
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.videoimageerror)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(onBackArrowClick, movieDetail)

            Row(
                modifier = Modifier.padding(top = 30.dp, bottom = 50.dp)
            ) {
                AsyncImage(
                    model = "$imageUrl${movieDetail.movie.posterPath}",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f)
                        .padding(start = 85.dp, end = 85.dp)
                        .clip(TMDBTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.videoimageerror)
                )
            }

            MovieInfo(movieDetail)

            Text(
                text = stringResource(R.string.overview),
                color = TMDBTheme.colors.white,
                style = TMDBTheme.typography.subtitle1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp, top = 24.dp, start = 24.dp)
            )
        }
    }
}

@Composable
private fun MovieInfo(movieDetail: DetailMovieWithAllRelations) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        RowWithIconAndText(
            iconId = R.drawable.calender,
            text = movieDetail.detailEntity.releaseDate.split(
                "-"
            )[0]
        )
        Divider(
            color = TMDBTheme.colors.gray,
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .align(Alignment.CenterVertically)
        )
        RowWithIconAndText(
            iconId = R.drawable.clock,
            text = "${movieDetail.detailEntity.runtime} Minutes"
        )
        Divider(
            color = TMDBTheme.colors.gray,
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .align(Alignment.CenterVertically)
        )
        RowWithIconAndText(
            iconId = R.drawable.film,
            text = movieDetail.genres[0].genreName
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        val roundedVote =
            movieDetail.movie.voteAverage.toBigDecimal()
                .setScale(1, RoundingMode.FLOOR)?.toDouble()
        RowWithIconAndText(
            text = roundedVote.toString(),
            iconId = R.drawable.star,
            iconColor = TMDBTheme.colors.secondary,
            textColor = TMDBTheme.colors.secondary
        )
    }
}

@Composable
private fun TopBar(
    onBackArrowClick: () -> Unit,
    movieDetail: DetailMovieWithAllRelations
) {
    var showDialog by remember { mutableStateOf(false) }
    Log.d("test", movieDetail.detailEntity.externalIds.toString())

    if (showDialog) {
        val isInstagramIdNotNull =
            movieDetail.detailEntity.externalIds[0] != stringResource(R.string.null_text)
        val isTwitterIdNotNull =
            movieDetail.detailEntity.externalIds[1] != stringResource(R.string.null_text)

        val isIMDBIdNotNull =
            movieDetail.detailEntity.externalIds[2] != stringResource(R.string.null_text)

        ShareDialog(isInstagramIdNotNull, isTwitterIdNotNull, isIMDBIdNotNull) {
            showDialog = it
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        IconButton(
            onClick = onBackArrowClick,
            Modifier
                .clip(TMDBTheme.shapes.rounded)
                .background(TMDBTheme.colors.surface)
                .align(Alignment.CenterStart),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                contentDescription = null,
                tint = TMDBTheme.colors.white
            )
        }

        Text(
            text = movieDetail.movie.title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.Center)
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .clip(TMDBTheme.shapes.rounded)
                    .background(TMDBTheme.colors.surface),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.heart),
                    contentDescription = null,
                    tint = TMDBTheme.colors.error
                )
            }

            IconButton(
                onClick = { showDialog = true },
                Modifier
                    .clip(TMDBTheme.shapes.rounded)
                    .background(TMDBTheme.colors.surface),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.share),
                    contentDescription = null,
                    tint = TMDBTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun ShareDialog(
    isInstagramIdNotNull: Boolean,
    isTwitterIdNotNull: Boolean,
    isIMDBIdNotNull: Boolean,
    changeShowDialog: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = { changeShowDialog(false) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(TMDBTheme.shapes.large)
                .background(TMDBTheme.colors.surface)
                .fillMaxWidth()
                .aspectRatio(1.4f)
                .padding(top = 12.dp)
        ) {
            IconButton(
                onClick = { changeShowDialog(false) },
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.End)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.close),
                    contentDescription = "close",
                    tint = TMDBTheme.colors.gray
                )
            }
            Text(
                text = stringResource(R.string.open_in),
                style = TMDBTheme.typography.h6,
                color = TMDBTheme.colors.white,
                modifier = Modifier.padding(
                    bottom = 15.dp
                )
            )

            Divider(
                modifier = Modifier
                    .padding(horizontal = 30.dp),
                color = TMDBTheme.colors.background
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    enabled = isInstagramIdNotNull
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.instagram),
                        contentDescription = "share instagram link",
                        alpha = if (isInstagramIdNotNull) 1f else 0.3f
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    enabled = isTwitterIdNotNull
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.twitter),
                        contentDescription = "share twitter link",
                        alpha = if (isTwitterIdNotNull) 1f else 0.3f
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    enabled = isIMDBIdNotNull
                ) {
                    Image(
                        painter = painterResource(R.drawable.imdb),
                        contentDescription = "share IMDB link",
                        alpha = if (isIMDBIdNotNull) 1f else 0.3f
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.tmdb),
                        contentDescription = "share TMDB link"
                    )
                }
            }
        }
    }
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = false, radius = RippleRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentAlpha = if (enabled) LocalContentAlpha.current else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha, content = content)
    }
}

private val RippleRadius = 24.dp
