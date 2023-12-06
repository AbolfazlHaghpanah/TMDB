package com.hooshang.tmdb.feature.favorite.ui

import androidx.compose.runtime.Immutable
import com.hooshang.tmdb.core.ui.ViewState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf


data class FavoriteMovie(
    val id: Int = -1,
    val title: String = "",
    val genres: String,
    val backdropPath: String,
    val voteAverage: Double
)

@Immutable
data class FavoriteState(
    val movies :PersistentList<FavoriteMovie> = persistentListOf()
) : ViewState