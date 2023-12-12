package com.hooshang.tmdb.feature.favorite.ui.contracts

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface FavoriteActions : ViewAction {
    data class OpenBottomSheet(val id: Int) : FavoriteActions
    data class NavigateToDetails(val id: Int) : FavoriteActions
}