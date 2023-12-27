package com.hooshang.tmdb.core.ui.component

import androidx.annotation.DrawableRes
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.utils.StringResWrapper
import com.hooshang.tmdb.navigation.AppScreens

sealed class BottomNavigationItems(
    val label: StringResWrapper,
    @DrawableRes val icon: Int,
    val route: String
) {
    data object Home :
        BottomNavigationItems(
            label = StringResWrapper(R.string.label_home),
            icon = R.drawable.ic_home,
            route = AppScreens.Home.route
        )

    data object Search :
        BottomNavigationItems(
            label = StringResWrapper(R.string.label_search),
            icon = R.drawable.ic_search,
            route = AppScreens.Search.route
        )

    data object Favorite :
        BottomNavigationItems(
            label = StringResWrapper(R.string.label_favorite),
            icon = R.drawable.ic_heart,
            route = AppScreens.Favorite.route
        )
}