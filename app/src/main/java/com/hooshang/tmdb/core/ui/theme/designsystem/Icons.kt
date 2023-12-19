package com.hooshang.tmdb.core.ui.theme.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import com.hooshang.tmdb.R

@Immutable
data class Icons(
    val arrowBack: Int = R.drawable.ic_arrow_back,
    val film: Int = R.drawable.ic_film,
    val calendar: Int = R.drawable.ic_calender,
    val home: Int = R.drawable.ic_home,
    val noResult: Int = R.drawable.ic_no_result,
    val clock: Int = R.drawable.ic_clock,
    val share: Int = R.drawable.ic_share,
    val heart: Int = R.drawable.ic_heart,
    val search: Int = R.drawable.ic_search,
    val folder: Int = R.drawable.ic_folder,
    val star: Int = R.drawable.ic_star,
    val close: Int = R.drawable.ic_close,
    val illustration: Int = R.drawable.ic_illustration,
    val layer: Int = R.drawable.ic_layer,
    val heartBorder: Int = R.drawable.ic_heart_border
)

val LocalIcons = staticCompositionLocalOf {
    Icons()
}