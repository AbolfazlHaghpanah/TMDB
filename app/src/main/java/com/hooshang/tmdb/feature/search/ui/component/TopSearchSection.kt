package com.hooshang.tmdb.feature.search.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.theme.designsystem.Theme

@Composable
fun TopSearchSection(
    searchString: String,
    onSearchChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = searchString,
            onValueChange = {
                onSearchChange(it)
            },
            maxLines = 2,
            shape = Theme.shapes.veryLarge,
            leadingIcon = {
                IconWrapper()
            },
            modifier = Modifier
                .clip(Theme.shapes.veryLarge)
                .border(0.dp, Theme.colors.surface)
                .background(color = Theme.colors.surface, shape = Theme.shapes.veryLarge)
                .weight(2f),
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
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = R.string.label_cancel),
                style = Theme.typography.caption,
                color = Theme.colors.white,
                textAlign = TextAlign.End
            )
        }
    }

}

@Composable
private fun IconWrapper() {
    Icon(
        imageVector = ImageVector.vectorResource(id = Theme.icons.search),
        contentDescription = null
    )
}