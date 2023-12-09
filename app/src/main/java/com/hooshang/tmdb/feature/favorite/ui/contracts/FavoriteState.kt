package com.hooshang.tmdb.feature.favorite.ui.contracts

import androidx.compose.runtime.Immutable
import com.hooshang.tmdb.core.ui.ViewState
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf


@Immutable
data class FavoriteState(
    val movies: PersistentList<FavoriteMovieDomainModel> = persistentListOf()
) : ViewState