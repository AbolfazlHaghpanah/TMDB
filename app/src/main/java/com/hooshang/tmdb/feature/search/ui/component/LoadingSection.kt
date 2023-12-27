package com.hooshang.tmdb.feature.search.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun LoadingSection() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = TMDBTheme.colors.primary,
        )
    }
}
