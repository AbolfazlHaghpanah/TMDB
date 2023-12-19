package com.hooshang.tmdb.feature.detail.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.component.TextIcon
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.core.utils.imageUrl
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import java.math.RoundingMode

@Composable
fun DetailTopWithGradient(
    detailsState: MovieDetailDomainModel,
    onBackArrowClick: () -> Unit,
    onFavoriteIconClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundImage(detailsState.posterPath)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(onBackArrowClick, detailsState, onFavoriteIconClick)

            Row(
                modifier = Modifier.padding(top = 30.dp, bottom = 50.dp)
            ) {
                ForegroundImage(detailsState.posterPath)
            }

            MovieInfo(detailsState)

            Text(
                text = stringResource(R.string.label_overview),
                color = Theme.colors.white,
                style = Theme.typography.subtitle1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp, top = 24.dp, start = 24.dp)
            )
        }
    }
}

@Composable
private fun ForegroundImage(movieDetailPosterPath: String) {
    AsyncImage(
        model = "$imageUrl${movieDetailPosterPath}",
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.1f)
            .padding(start = 85.dp, end = 85.dp)
            .clip(Theme.shapes.medium),
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.img_video_image_error)
    )
}

@Composable
private fun BackgroundImage(movieDetailPosterPath: String) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Theme.colors.background.copy(alpha = 0.57f),
            Theme.colors.background.copy(alpha = 1f)
        )
    )

    AsyncImage(
        model = "$imageUrl${movieDetailPosterPath}",
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
        error = painterResource(id = R.drawable.img_video_image_error)
    )
}

@Composable
private fun MovieInfo(movieDetailDomainModel: MovieDetailDomainModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        TextIcon(
            iconId = Theme.icons.calendar,
            text = movieDetailDomainModel.releaseDate.split(
                "-"
            )[0]
        )
        Divider(
            color = Theme.colors.gray,
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .align(Alignment.CenterVertically)
        )
        TextIcon(
            iconId = Theme.icons.clock,
            text = "${movieDetailDomainModel.runtime} Minutes"
        )
        if (movieDetailDomainModel.genres.isNotEmpty()) {
            Divider(
                color = Theme.colors.gray,
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .align(Alignment.CenterVertically)
            )
            TextIcon(
                iconId = Theme.icons.film,
                text = movieDetailDomainModel.genres[0].second
            )
        }
    }

    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        val roundedVote =
            movieDetailDomainModel.voteAverage.toBigDecimal()
                .setScale(1, RoundingMode.FLOOR)?.toDouble()

        TextIcon(
            text = roundedVote.toString(),
            iconId = Theme.icons.star,
            iconColor = Theme.colors.secondary,
            textColor = Theme.colors.secondary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TopBar(
    onBackArrowClick: () -> Unit,
    movieDetailDomainModel: MovieDetailDomainModel,
    onFavoriteIconClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    if (showDialog) {
        ShareDialog(
            movieDetailDomainModel.externalIds,
            movieDetailDomainModel.id,
            movieDetailDomainModel.title,
            { requestString ->
                uriHandler.openUri(uri = requestString)
            }
        ) {
            showDialog = it
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        TMDBIconButton(
            onClick = onBackArrowClick,
            Modifier
                .clip(Theme.shapes.rounded)
                .background(Theme.colors.surface)
                .align(Alignment.CenterStart),
        ) {
            IconWrapper(
                icon = Theme.icons.arrowBack,
                tintColor = Theme.colors.white
            )
        }

        Text(
            text = movieDetailDomainModel.title,
            style = Theme.typography.subtitle1,
            color = Theme.colors.white,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(50.dp, 200.dp)
                .basicMarquee(),
            maxLines = 1
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val suitableIcon =
                if (!movieDetailDomainModel.isFavorite) Theme.icons.heartBorder else Theme.icons.heart
            TMDBIconButton(
                onClick = onFavoriteIconClick,
                Modifier
                    .clip(Theme.shapes.rounded)
                    .background(Theme.colors.surface),
            ) {
                IconWrapper(
                    icon = suitableIcon,
                    tintColor = Theme.colors.error
                )
            }

            TMDBIconButton(
                onClick = { showDialog = true },
                Modifier
                    .clip(Theme.shapes.rounded)
                    .background(Theme.colors.surface),
            ) {
                IconWrapper(
                    icon = Theme.icons.share,
                    tintColor = Theme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun IconWrapper(
    @DrawableRes icon: Int,
    tintColor: Color
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = icon),
        contentDescription = null,
        tint = tintColor
    )
}

@Composable
private fun ShareDialog(
    externalIds: List<String>,
    movieId: Int?,
    movieTitle: String?,
    shareIconOnClock: (String) -> Unit,
    changeShowDialog: (Boolean) -> Unit
) {
    val imdbIndex = 0
    val instagramIndex = 1
    val twitterIndex = 2

    val isInstagramIdNotNull =
        externalIds[instagramIndex] != stringResource(R.string.cmp_null_text)
    val isTwitterIdNotNull =
        externalIds[twitterIndex] != stringResource(R.string.cmp_null_text)
    val isIMDBIdNotNull =
        externalIds[imdbIndex] != stringResource(R.string.cmp_null_text)

    Dialog(
        onDismissRequest = { changeShowDialog(false) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(Theme.shapes.large)
                .background(Theme.colors.surface)
                .fillMaxWidth()
                .aspectRatio(1.4f)
                .padding(top = 12.dp)
        ) {
            TMDBIconButton(
                onClick = { changeShowDialog(false) },
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.End)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(Theme.icons.close),
                    contentDescription = "close",
                    tint = Theme.colors.gray
                )
            }
            Text(
                text = stringResource(R.string.label_open_in),
                style = Theme.typography.h6,
                color = Theme.colors.white,
                modifier = Modifier.padding(
                    bottom = 15.dp
                )
            )

            Divider(
                modifier = Modifier
                    .padding(horizontal = 30.dp),
                color = Theme.colors.background
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                TMDBIconButton(
                    onClick = {
                        shareIconOnClock("https://www.instagram.com/thegodfathermovie/${externalIds[instagramIndex]}")
                    },
                    enabled = isInstagramIdNotNull
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isInstagramIdNotNull,
                        image = R.drawable.ic_instagram,
                        contentDescription = "share instagram link"
                    )
                }
                TMDBIconButton(
                    onClick = {
                        shareIconOnClock("https://twitter.com/${externalIds[twitterIndex]}")
                    },
                    enabled = isTwitterIdNotNull
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isTwitterIdNotNull,
                        image = R.drawable.ic_twitter,
                        contentDescription = "share twitter link"
                    )
                }
                TMDBIconButton(
                    onClick = {
                        shareIconOnClock("https://www.imdb.com/title/${externalIds[imdbIndex]}")
                    },
                    enabled = isIMDBIdNotNull
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isIMDBIdNotNull,
                        image = R.drawable.ic_imdb,
                        contentDescription = "share IMDB link"
                    )
                }
                TMDBIconButton(onClick = {
                    val titleSplit = movieTitle?.split(" ")
                    val titleSplitPlusDash = titleSplit?.joinToString(separator = "-") { it }
                    if (titleSplitPlusDash != null) {
                        shareIconOnClock("https://www.themoviedb.org/collection/${movieId}-${titleSplitPlusDash}")
                    }
                }) {
                    ImageWrapper(
                        shouldNotHaveAlpha = true,
                        image = R.drawable.ic_tmdb,
                        contentDescription = "share TMDB link"
                    )
                }
            }
        }
    }
}

@Composable
private fun ImageWrapper(
    shouldNotHaveAlpha: Boolean,
    @DrawableRes image: Int,
    contentDescription: String
) {
    Image(
        painter = painterResource(image),
        contentDescription = contentDescription,
        alpha = if (shouldNotHaveAlpha) 1f else 0.3f
    )
}

@Composable
fun TMDBIconButton(
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
                indication = rememberRipple(bounded = false, radius = 24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentAlpha = if (enabled) LocalContentAlpha.current else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha, content = content)
    }
}