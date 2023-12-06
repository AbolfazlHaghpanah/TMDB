package com.hooshang.tmdb.feature.favorite.ui

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface FavoriteActions : ViewAction {
    data object TryAgain : FavoriteActions
    data class OpenBottomSheet(val id: Int) : FavoriteActions
    data class NavigateToDetails(val id: Int) : FavoriteActions
}