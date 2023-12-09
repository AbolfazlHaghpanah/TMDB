package com.hooshang.tmdb.feature.detail.ui.contract

import androidx.compose.runtime.Immutable
import com.hooshang.tmdb.core.ui.ViewState
import com.hooshang.tmdb.feature.detail.domain.model.CastOrCrewDomainModel
import com.hooshang.tmdb.feature.detail.domain.model.SimilarMovieDomainModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class DetailsState(
    val id: Int = -1,
    val title: String = "",
    val overview: String = "",
    val voteAverage: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val runtime: Int = 0,
    val genres: PersistentList<Pair<Int, String>> = persistentListOf(),
    val externalIds: PersistentList<String> = persistentListOf(),
    val credits: PersistentList<CastOrCrewDomainModel> = persistentListOf(),
    val similar: PersistentList<SimilarMovieDomainModel> = persistentListOf(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true
) : ViewState

