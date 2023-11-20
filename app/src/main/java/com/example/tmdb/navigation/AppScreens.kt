package com.example.tmdb.navigation

sealed class AppScreens(
    val route: String
) {

    object Home : AppScreens("home")
    object Search : AppScreens("search")
    object Detail : AppScreens("details/{id}") {
        fun createRoute(id: Int): String {
            return "details/$id"
        }
    }

    object Favorite : AppScreens("favorite")

    object BottomSheet : AppScreens("BottomSheet/{id}") {
        fun createRoute(id: Int): String {
            return "BottomSheet/$id"
        }
    }
}