package com.hooshang.tmdb.feature.favorite.ui.deletebottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.favorite.ui.component.TrashIcon

@Composable
fun TMDBModalBottomSheet(
    navController: NavController
) {
    TMDBModalBottomSheet(
        navController = navController,
        viewModel = hiltViewModel()
    )
}

@Composable
fun TMDBModalBottomSheet(
    navController: NavController,
    viewModel: TMDBModalBottomSheetViewModel
) {
    val onDismiss: () -> Unit = remember {
        {
            navController.popBackStack()
        }
    }

    val onDelete = remember {
        {
            viewModel.deleteMovie()
        }
    }

    TMDBModalBottomSheet(
        onDismiss = onDismiss,
        onDelete = onDelete
    )
}

@Composable
private fun TMDBModalBottomSheet(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TrashIcon()

        Text(
            text = stringResource(R.string.are_you_sure),
            style = TMDBTheme.typography.h6,
            color = TMDBTheme.colors.white
        )

        Text(
            modifier = Modifier
                .padding(top = 18.dp, bottom = 48.dp, start = 56.dp, end = 56.dp),
            text = stringResource(R.string.delete_alert),
            style = TMDBTheme.typography.caption,
            color = TMDBTheme.colors.gray,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = TMDBTheme.shapes.rounded,
            onClick = onDismiss

        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(R.string.cancel),
                style = TMDBTheme.typography.button
            )
        }

        TextButton(
            onClick = {
                onDelete()
                onDismiss()
            }
        ) {

            Text(
                text = stringResource(R.string.delete),
                style = TMDBTheme.typography.button,
                color = TMDBTheme.colors.error
            )
        }
    }
}

