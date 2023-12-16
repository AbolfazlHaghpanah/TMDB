package com.hooshang.tmdb.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hooshang.tmdb.feature.favorite.ui.deletebottomsheet.TMDBModalBottomSheet
import com.hooshang.tmdb.feature.detail.ui.DetailsScreen
import com.hooshang.tmdb.feature.favorite.ui.FavoriteScreen
import com.hooshang.tmdb.feature.home.ui.HomeScreen
import com.hooshang.tmdb.feature.search.ui.SearchScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(AppScreens.Home.route) {
        HomeScreen(navController = navController)
    }

    composable(AppScreens.Detail.route) {
        DetailsScreen(navController = navController)
    }

    composable(AppScreens.Search.route) {
        SearchScreen(navController = navController)
    }

    composable(AppScreens.Favorite.route) {
        FavoriteScreen(navController = navController)
    }

    bottomSheet(AppScreens.BottomSheet.route) {
        TMDBModalBottomSheet(navController)
    }
}