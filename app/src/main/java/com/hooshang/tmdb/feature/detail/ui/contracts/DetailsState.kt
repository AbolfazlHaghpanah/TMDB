package com.hooshang.tmdb.feature.detail.ui.contracts

import androidx.compose.runtime.Immutable
import com.hooshang.tmdb.core.ui.ViewState
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel

@Immutable
data class DetailsState(
    val movie: MovieDetailDomainModel = MovieDetailDomainModel(
        id = -1,
        title = "",
        overview = "",
        voteAverage = 0.0,
        posterPath = "",
        releaseDate = "",
        runtime = 0,
        genres = listOf(),
        externalIds = listOf(),
        credits = listOf(),
        similar = listOf(),
        isFavorite = false
    ),
    val isLoading: Boolean = true,
) : ViewState

