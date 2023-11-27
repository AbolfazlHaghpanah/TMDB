package com.example.tmdb.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TextIcon(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconId: Int,
    iconColor: Color? = null,
    textColor: Color? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .zIndex(1f)
            .then(modifier)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
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
