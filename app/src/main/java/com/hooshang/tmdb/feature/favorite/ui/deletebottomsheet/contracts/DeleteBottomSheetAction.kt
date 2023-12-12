package com.hooshang.tmdb.feature.favorite.ui.deletebottomsheet.contracts

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface DeleteBottomSheetAction : ViewAction {
    data object DeleteFromFavorite : DeleteBottomSheetAction
    data object Dismiss : DeleteBottomSheetAction
}