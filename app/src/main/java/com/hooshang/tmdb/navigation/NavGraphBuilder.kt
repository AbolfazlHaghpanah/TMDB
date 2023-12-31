package com.hooshang.tmdb.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
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
    animatedComposable(AppScreens.Home.route) {
        HomeScreen(navController = navController)
    }

    animatedComposable(AppScreens.Detail.route) {
        DetailsScreen(navController = navController)
    }

    animatedComposable(AppScreens.Search.route) {
        SearchScreen(navController = navController)
    }

    animatedComposable(AppScreens.Favorite.route) {
        FavoriteScreen(navController = navController)
    }

    bottomSheet(AppScreens.BottomSheet.route) {
        TMDBModalBottomSheet(navController)
    }
}

fun NavGraphBuilder.animatedComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        enterTransition = { fadeIn(tween(500)) },
        exitTransition = { fadeOut(tween(500)) },
        popExitTransition = { fadeOut(tween(500)) },
        popEnterTransition = { fadeIn(tween(500)) },
        route = route,
    ) {
        content(this,it)
    }
}