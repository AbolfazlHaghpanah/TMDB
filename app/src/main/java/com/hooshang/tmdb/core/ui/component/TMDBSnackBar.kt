package com.hooshang.tmdb.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TMDBSnackBar(
    message: String,
    actionLabel: String? = null,
    performAction: (() -> Unit)? = null
) {
    Snackbar(
        modifier = Modifier
            .padding(16.dp),
        backgroundColor = TMDBTheme.colors.surface,
        shape = TMDBTheme.shapes.large,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(
                    if (actionLabel != null && performAction != null) 0.7f
                    else 1f
                ),
                text = message,
                style = TMDBTheme.typography.subtitle2,
                color = TMDBTheme.colors.white
            )
            if (actionLabel != null && performAction != null) {
                TextButton(onClick = performAction) {
                    Text(
                        modifier = Modifier,
                        text = actionLabel,
                        style = TMDBTheme.typography.subtitle2,
                        color = TMDBTheme.colors.primary
                    )
                }
            }
        }
    }
}