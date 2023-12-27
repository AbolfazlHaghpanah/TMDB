package com.hooshang.tmdb.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TextIcon(
    text: String,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
    textColor: Color? = null
) {
    Row(
        modifier = Modifier
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
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
