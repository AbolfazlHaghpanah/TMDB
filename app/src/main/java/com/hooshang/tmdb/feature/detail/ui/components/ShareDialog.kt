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
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.core.utils.imdb_uri
import com.hooshang.tmdb.core.utils.instagram_uri
import com.hooshang.tmdb.core.utils.tmdb_uri
import com.hooshang.tmdb.core.utils.twitter_uri

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
        externalIds[instagramIndex] != stringResource(R.string.cmp_null_text)
    val isTwitterIdNotNull =
        externalIds[twitterIndex] != stringResource(R.string.cmp_null_text)
    val isIMDBIdNotNull =
        externalIds[imdbIndex] != stringResource(R.string.cmp_null_text)

    Dialog(
        onDismissRequest = { onDismiss() }
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
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.End),
                onClick = { onDismiss() },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(Theme.icons.close),
                    contentDescription = stringResource(R.string.label_close),
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
                    enabled = isInstagramIdNotNull,
                    onClick = {
                        onClock("$instagram_uri${externalIds[instagramIndex]}")
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isInstagramIdNotNull,
                        image = R.drawable.ic_instagram,
                        contentDescription = stringResource(R.string.prefix_share_instagram_link)
                    )
                }

                TMDBIconButton(
                    enabled = isTwitterIdNotNull,
                    onClick = {
                        onClock("$twitter_uri${externalIds[twitterIndex]}")
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isTwitterIdNotNull,
                        image = R.drawable.ic_twitter,
                        contentDescription = stringResource(R.string.prefix_share_twitter_link)
                    )
                }

                TMDBIconButton(
                    enabled = isIMDBIdNotNull,
                    onClick = {
                        onClock("$imdb_uri${externalIds[imdbIndex]}")
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = isIMDBIdNotNull,
                        image = R.drawable.ic_imdb,
                        contentDescription = stringResource(R.string.prefix_share_imdb_link)
                    )
                }

                TMDBIconButton(
                    onClick = {
                        val titleSplit = movieTitle?.split(" ")
                        val titleSplitPlusDash = titleSplit?.joinToString(separator = "-") { it }
                        if (titleSplitPlusDash != null) {
                            onClock("$tmdb_uri${movieId}-${titleSplitPlusDash}")
                        }
                    }
                ) {
                    ImageWrapper(
                        shouldNotHaveAlpha = true,
                        image = R.drawable.ic_tmdb,
                        contentDescription = stringResource(R.string.prefix_share_tmdb_link)
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