package com.example.tmdb.core.ui.bottomsheet.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TrashIcon() {
    Image(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 16.dp),
        painter = painterResource(id = TMDBTheme.icons.illustration),
        contentDescription = null
    )
}