package com.example.tmdb.navigation

sealed class AppScreens(
    val route: String
) {

    object Home : AppScreens("home")
    object Search : AppScreens("search")
    object Detail : AppScreens("detail" )
    object Favorite : AppScreens("favorite" )
}