package com.example.tmdb.feature.detail.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        contentColor = TMDBTheme.colors.background.copy(alpha = 1f),
        backgroundColor = TMDBTheme.colors.background.copy(alpha = 1f),
        modifier = Modifier.background(TMDBTheme.colors.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(TMDBTheme.colors.background)
                .navigationBarsPadding()
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
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

                Image(
                    painter = painterResource(id = R.drawable.testimage),
                    contentDescription = null,
                    Modifier
                        .fillMaxSize()
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        },
                    contentScale = ContentScale.Crop
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(vertical = 12.dp, horizontal = 24.dp)
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ },
                            Modifier
                                .clip(TMDBTheme.shapes.rounded)
                                .background(TMDBTheme.colors.surface)

                                .align(Alignment.CenterStart),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrowback),
                                contentDescription = null,
                                tint = TMDBTheme.colors.white
                            )
                        }

                        Text(
                            text = "Riverdale",
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
                                onClick = { /*TODO*/ },
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
                    Row(
                        modifier = Modifier.padding(top = 30.dp, bottom = 50.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.testimage),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.1f)
                                .padding(start = 85.dp, end = 85.dp)
                                .clip(TMDBTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        RowWithIconAndText(
                            iconId = R.drawable.calender,
                            text = "2021"
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
                            text = "148 Minutes"
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
                            text = "Action"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        RowWithIconAndText(
                            text = "4.5",
                            iconId = R.drawable.star,
                            iconColor = TMDBTheme.colors.secondary,
                            textColor = TMDBTheme.colors.secondary
                        )
                    }


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



            Text(
                text = "Originally a story from Archie Comics which started in 1941, Riverdale centres around a group of high school students who are shocked by the death of classmate, Jason Blossom. Together theyunravel the secrets of Riverdale and who",
                color = TMDBTheme.colors.whiteGray,
                style = TMDBTheme.typography.subtitle2,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Text(
                text = stringResource(R.string.cast_and_crew),
                style = TMDBTheme.typography.subtitle1,
                color = TMDBTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(
                        top = 24.dp,
                        start = 24.dp
                    )
            )
            LazyRow(
                contentPadding = PaddingValues(
                    start = 24.dp,
                    top = 16.dp
                )
            ) {
                items(5) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.testprofile),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(TMDBTheme.shapes.rounded)
                        )
                        Column(
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Text(
                                text = "Jon Watts",
                                style = TMDBTheme.typography.body1,
                                color = TMDBTheme.colors.white
                            )
                            Text(
                                text = "Directors",
                                style = TMDBTheme.typography.overLine,
                                color = TMDBTheme.colors.gray
                            )
                        }
                    }
                }
            }

            Text(
                text = stringResource(R.string.similar_movies),
                style = TMDBTheme.typography.subtitle1,
                color = TMDBTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(
                        top = 24.dp,
                        start = 24.dp
                    )
            )

            LazyRow(
                contentPadding = PaddingValues(
                    start = 24.dp,
                    top = 16.dp
                )
            ) {
                items(5) {
                    Column {
                        Box {
                            Image(
                                painter = painterResource(id = R.drawable.spidermantestimage),
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
                                    .aspectRatio(0.7f)
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clip(TMDBTheme.shapes.small)
                                    .background(TMDBTheme.colors.surface.copy(alpha = 0.7f))
                            ) {
                                RowWithIconAndText(
                                    text = "4.5",
                                    iconId = R.drawable.star,
                                    iconColor = TMDBTheme.colors.secondary,
                                    textColor = TMDBTheme.colors.secondary
                                )
                            }
                        }
                        Text(
                            text = "Spider-Man No..",
                            style = TMDBTheme.typography.body1,
                            color = TMDBTheme.colors.white,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 12.dp, bottom = 4.dp)
                        )
                        Text(
                            text = "Action",
                            style = TMDBTheme.typography.overLine,
                            color = TMDBTheme.colors.gray,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowWithIconAndText(
    text: String,
    @DrawableRes iconId: Int,
    iconColor: Color? = null,
    textColor: Color? = null
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = iconColor ?: TMDBTheme.colors.gray,
            )
            Text(
                text = text,
                color = textColor ?: TMDBTheme.colors.gray,
                style = TMDBTheme.typography.body2
            )
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