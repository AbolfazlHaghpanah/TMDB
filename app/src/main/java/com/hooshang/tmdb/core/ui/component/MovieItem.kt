package com.hooshang.tmdb.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme
import com.hooshang.tmdb.core.utils.ifShimmerActive
import com.hooshang.tmdb.core.utils.image_url

@Composable
fun MovieCard(
    title: String,
    image: String,
    genres: String,
    vote: String,
    modifier: Modifier = Modifier,
    isShimmer: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(Theme.shapes.medium)
            .clickable(onClick = onClick)
            .background(
                Theme.colors.surface,
                Theme.shapes.medium
            )
            .width(140.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp)
        ) {

            TextIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(Theme.shapes.small)
                    .background(Theme.colors.surface.copy(alpha = 0.7f))
                    .padding(8.dp, 4.dp)
                    .ifShimmerActive(isShimmer),
                text = vote,
                iconId = Theme.icons.star,
                iconColor = Theme.colors.secondary,
                textColor = Theme.colors.secondary
            )

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = image_url + image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                error = if (isShimmer) null else painterResource(id = R.drawable.img_video_image_error)
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
                .ifShimmerActive(isShimmer),
            text = title,
            style = Theme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Theme.colors.white
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .ifShimmerActive(isShimmer),
            text = genres,
            style = Theme.typography.overLine,
            maxLines = 1,
            color = Theme.colors.gray,
            overflow = TextOverflow.Ellipsis
        )
    }
}