package com.hooshang.tmdb.feature.favorite.ui.deletebottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme
import com.hooshang.tmdb.feature.favorite.ui.deletebottomsheet.contracts.DeleteBottomSheetAction

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
    val onAction: (DeleteBottomSheetAction) -> Unit = { action ->
        when (action) {
            is DeleteBottomSheetAction.Dismiss -> navController.popBackStack()
            else -> viewModel.onAction(action)
        }
    }

    TMDBModalBottomSheet(onAction = onAction)
}

@Composable
private fun TMDBModalBottomSheet(
    onAction: (DeleteBottomSheetAction) -> Unit
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(bottom = 64.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 16.dp),
            painter = painterResource(id = TMDBTheme.icons.illustration),
            contentDescription = null
        )

        Text(
            text = stringResource(R.string.desc_are_you_sure),
            style = TMDBTheme.typography.h6,
            color = TMDBTheme.colors.white
        )

        Text(
            modifier = Modifier
                .padding(top = 18.dp, bottom = 48.dp, start = 56.dp, end = 56.dp),
            text = stringResource(R.string.desc_delete_alert),
            style = TMDBTheme.typography.caption,
            color = TMDBTheme.colors.gray,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = TMDBTheme.shapes.rounded,
            onClick = { onAction(DeleteBottomSheetAction.Dismiss) }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(R.string.label_cancel),
                style = TMDBTheme.typography.button
            )
        }

        TextButton(
            onClick = {
                onAction(DeleteBottomSheetAction.DeleteFromFavorite)
                onAction(DeleteBottomSheetAction.Dismiss)
            }
        ) {
            Text(
                text = stringResource(R.string.label_delete),
                style = TMDBTheme.typography.button,
                color = TMDBTheme.colors.error
            )
        }
    }
}