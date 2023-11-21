package com.example.tmdb.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tmdb.core.ui.bottomsheet.TMDBModalBottomSheet
import com.example.tmdb.feature.detail.ui.DetailScreen
import com.example.tmdb.feature.favorite.ui.FavoriteScreen
import com.example.tmdb.feature.home.ui.HomeScreen
import com.example.tmdb.feature.search.ui.SearchScreen
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
        DetailScreen(navController = navController)
    }

    composable(AppScreens.Favorite.route) {
        FavoriteScreen(navController = navController)
    }

    composable(AppScreens.Search.route) {
        SearchScreen(navController = navController)
    }

    bottomSheet(AppScreens.BottomSheet.route) {
        TMDBModalBottomSheet(navController)
    }
}