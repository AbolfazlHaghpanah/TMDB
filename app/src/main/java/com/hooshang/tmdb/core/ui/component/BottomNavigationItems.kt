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
            StringResWrapper(R.string.label_home),
            R.drawable.ic_home,
            AppScreens.Home.route
        )

    data object Search :
        BottomNavigationItems(
            StringResWrapper(R.string.label_search),
            R.drawable.ic_search,
            AppScreens.Search.route
        )

    data object Favorite :
        BottomNavigationItems(
            StringResWrapper(R.string.label_favorite),
            R.drawable.ic_heart,
            AppScreens.Favorite.route
        )
}