package com.hooshang.tmdb.feature.favorite.ui.deletebottomsheet.contracts

import androidx.compose.runtime.Immutable
import com.hooshang.tmdb.core.ui.ViewState

@Immutable
data class DeleteBottomSheetState(
    val id : Int = -1
) : ViewState