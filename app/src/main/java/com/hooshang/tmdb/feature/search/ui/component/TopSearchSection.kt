package com.hooshang.tmdb.feature.search.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TopSearchSection(
    searchString: String,
    onSearchChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = searchString,
            onValueChange = {
                onSearchChange(it)
            },
            maxLines = 2,
            shape = TMDBTheme.shapes.veryLarge,
            leadingIcon = {
                IconWrapper()
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .border(0.dp, TMDBTheme.colors.surface)
                .background(color = TMDBTheme.colors.surface, shape = TMDBTheme.shapes.veryLarge)
                .width(292.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent
            )
        )
        TextButton(
            onClick = {
                if (searchString != "") {
                    onSearchChange("")
                }
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
        ) {
            Text(
                text = stringResource(id = R.string.cancel),
                style = TMDBTheme.typography.caption,
                color = TMDBTheme.colors.white,
                textAlign = TextAlign.End
            )
        }
    }

}

@Composable
private fun IconWrapper() {
    Icon(
        imageVector = ImageVector.vectorResource(id = TMDBTheme.icons.search),
        contentDescription = null
    )
}