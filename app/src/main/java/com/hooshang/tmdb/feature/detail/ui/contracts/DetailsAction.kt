package com.hooshang.tmdb.feature.detail.ui.contracts

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface DetailsAction : ViewAction {
    data object AddToFavorite : DetailsAction
    data object RemoveFromFavorite : DetailsAction
    data class NavigateToDetails(val id: Int) : DetailsAction
    data object Back : DetailsAction
    data object ShowLastSnackBar : DetailsAction
}