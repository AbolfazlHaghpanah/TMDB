package com.hooshang.tmdb.feature.detail.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.core.utils.imdbUri
import com.hooshang.tmdb.core.utils.instagramUri
import com.hooshang.tmdb.core.utils.tmdbUri
import com.hooshang.tmdb.core.utils.twitterUri

@Composable
fun ShareDialog(
    externalIds: List<String>,
    movieId: Int?,
    movieTitle: String?,
    onClock: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val imdbIndex = 0
    val instagramIndex = 1
    val twitterIndex = 2

    val isInstagramIdNotNull =
        externalIds[instagramIndex] != stringResource(R.string.null_text)
    val isTwitterIdNotNull =
        externalIds[twitterIndex] != stringResource(R.string.null_text)
    val isIMDBIdNotNull =
        externalIds[imdbIndex] != stringResource(R.string.null_text)

    Dialog(
        onDismissRequest = { onDismiss() }
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
            TMDBIconButton(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.End),
                onClick = { onDismiss() },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(TMDBTheme.icons.close),
                    contentDescription = stringResource(R.string.close),
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
                TMDBIconButton(
                    enabled = isInstagramIdNotNull,
                    onClick = {
                        onClock("$instagramUri${externalIds[instagramIndex]}")
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isInstagramIdNotNull,
                        image = R.drawable.instagram,
                        contentDescription = stringResource(R.string.share_instagram_link)
                    )
                }

                TMDBIconButton(
                    enabled = isTwitterIdNotNull,
                    onClick = {
                        onClock("$twitterUri${externalIds[twitterIndex]}")
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isTwitterIdNotNull,
                        image = R.drawable.twitter,
                        contentDescription = stringResource(R.string.share_twitter_link)
                    )
                }

                TMDBIconButton(
                    enabled = isIMDBIdNotNull,
                    onClick = {
                        onClock("$imdbUri${externalIds[imdbIndex]}")
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isIMDBIdNotNull,
                        image = R.drawable.imdb,
                        contentDescription = stringResource(R.string.share_imdb_link)
                    )
                }

                TMDBIconButton(
                    onClick = {
                        val titleSplit = movieTitle?.split(" ")
                        val titleSplitPlusDash = titleSplit?.joinToString(separator = "-") { it }
                        if (titleSplitPlusDash != null) {
                            onClock("$tmdbUri${movieId}-${titleSplitPlusDash}")
                        }
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = true,
                        image = R.drawable.tmdb,
                        contentDescription = stringResource(R.string.share_tmdb_link)
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