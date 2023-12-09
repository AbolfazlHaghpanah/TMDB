package com.hooshang.tmdb.feature.search.ui.contracts

import com.hooshang.tmdb.core.ui.ViewState
import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchResults: PersistentList<SearchMovieWithGenreDomainModel> = persistentListOf()
) : ViewState