package com.example.tmdb.feature.detail.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tmdb.R
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun DetailScreen(
    navController: NavController
) {
    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val gradient = Brush.verticalGradient(
                colors = listOf(
                    TMDBTheme.colors.background.copy(alpha = 0.57f),
                    TMDBTheme.colors.background.copy(alpha = 1f)
                )
            )
            Image(
                painter = painterResource(id = R.drawable.testimage),
                contentDescription = null,
                Modifier
                    .fillMaxHeight()
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.DstOut)
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
                                painter = painterResource(id = R.drawable.heart),
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
                                painter = painterResource(id = R.drawable.share),
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
    Row {
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
                color = textColor ?: TMDBTheme.colors.gray
            )
        }
    }
}