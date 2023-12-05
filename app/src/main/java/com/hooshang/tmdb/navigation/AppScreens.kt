package com.hooshang.tmdb.navigation

sealed class AppScreens(
    val route: String
) {

    data object Home : AppScreens("home")
    data object Search : AppScreens("search")
    data object Detail : AppScreens("details/{id}") {
        fun createRoute(id: Int): String {
            return "details/$id"
        }
    }

    data object Favorite : AppScreens("favorite")

    data object BottomSheet : AppScreens("BottomSheet/{id}") {
        fun createRoute(id: Int): String {
            return "BottomSheet/$id"
        }
    }
}