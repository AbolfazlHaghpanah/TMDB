package com.hooshang.tmdb.feature.detail.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBar(
    movieDetailDomainModel: MovieDetailDomainModel,
    onBackArrowClick: () -> Unit,
    onFavoriteIconClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    if (showDialog) {
        ShareDialog(
            externalIds = movieDetailDomainModel.externalIds,
            movieId = movieDetailDomainModel.id,
            movieTitle = movieDetailDomainModel.title,
            onClock = { requestString ->
                uriHandler.openUri(uri = requestString)
            },
            onDismiss = { showDialog = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(vertical = 12.dp, horizontal = 24.dp)
    ) {
        TMDBIconButton(
            modifier = Modifier
                .clip(TMDBTheme.shapes.rounded)
                .background(TMDBTheme.colors.surface)
                .align(Alignment.CenterStart),
            onClick = onBackArrowClick,
        ) {
            IconWrapper(
                icon = TMDBTheme.icons.arrowBack,
                tintColor = TMDBTheme.colors.white
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(50.dp, 200.dp)
                .basicMarquee(),
            text = movieDetailDomainModel.title,
            style = TMDBTheme.typography.subtitle1,
            color = TMDBTheme.colors.white,
            textAlign = TextAlign.Start,
            maxLines = 1
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val suitableIcon =
                if (!movieDetailDomainModel.isFavorite) TMDBTheme.icons.heartBorder else TMDBTheme.icons.heart
            TMDBIconButton(
                modifier = Modifier
                    .clip(TMDBTheme.shapes.rounded)
                    .background(TMDBTheme.colors.surface),
                onClick = onFavoriteIconClick
            ) {
                IconWrapper(
                    icon = suitableIcon,
                    tintColor = TMDBTheme.colors.error
                )
            }

            TMDBIconButton(
                modifier = Modifier
                    .clip(TMDBTheme.shapes.rounded)
                    .background(TMDBTheme.colors.surface),
                onClick = { showDialog = true },
            ) {
                IconWrapper(
                    icon = TMDBTheme.icons.share,
                    tintColor = TMDBTheme.colors.primary
                )
            }
        }
    }
}

@NonRestartableComposable
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