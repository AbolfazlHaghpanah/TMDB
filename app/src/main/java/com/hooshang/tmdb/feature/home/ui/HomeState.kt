package com.hooshang.tmdb.feature.home.ui

import com.hooshang.tmdb.core.ui.ViewState
import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel

data class HomeState(
    val nowPlayingMovies: List<HomeMovieDomainModel> = listOf(),
    val popularMovies: List<HomeMovieDomainModel> = listOf(),
    val topRatedMovies: List<HomeMovieDomainModel> = listOf()
) : ViewState