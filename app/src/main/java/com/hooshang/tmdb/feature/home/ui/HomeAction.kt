package com.hooshang.tmdb.feature.home.ui

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface HomeAction : ViewAction {
    data class NavigateToDetail(val id: Int) : HomeAction
}