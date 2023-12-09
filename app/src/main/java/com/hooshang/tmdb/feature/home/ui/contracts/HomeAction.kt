package com.hooshang.tmdb.feature.home.ui.contracts

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface HomeAction : ViewAction {
    data class NavigateToDetail(val id: Int) : HomeAction
    data object ShowLastSnackBar : HomeAction
}