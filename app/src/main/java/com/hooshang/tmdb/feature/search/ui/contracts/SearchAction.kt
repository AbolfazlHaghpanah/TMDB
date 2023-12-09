package com.hooshang.tmdb.feature.search.ui.contracts

import com.hooshang.tmdb.core.ui.ViewAction

sealed interface SearchAction : ViewAction {
    data class NavigateToDetail(val id: Int) : SearchAction
    data class OnSearch(val input: String) : SearchAction
    data object ShowLastSnackBar : SearchAction
}