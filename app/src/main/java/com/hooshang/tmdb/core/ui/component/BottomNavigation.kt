package com.hooshang.tmdb.core.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hooshang.tmdb.core.ui.theme.designsystem.TMDBTheme

@Composable
fun TMDBBottomNavigation(
    items: List<BottomNavigationItems>,
    isSelected: (String) -> Boolean,
    onNavItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
            .navigationBarsPadding(),
        backgroundColor = TMDBTheme.colors.background,
        elevation = 24.dp
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier
                    .fillMaxSize(),
                selected = isSelected(item.route),
                onClick = {
                    onNavItemClick(item.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier,
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.label.asString(),
                    )
                },
                label = {
                    Text(
                        text = item.label.asString(),
                        style = MaterialTheme.typography.caption
                    )
                },
                selectedContentColor = TMDBTheme.colors.primary,
                unselectedContentColor = TMDBTheme.colors.gray,
            )
        }
    }
}