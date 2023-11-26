package com.example.tmdb.feature.search.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun LoadingSection() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        CircularProgressIndicator(
            color = TMDBTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
