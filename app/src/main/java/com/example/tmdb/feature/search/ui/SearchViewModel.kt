package com.example.tmdb.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.search.network.SearchApi
import com.example.tmdb.feature.search.network.json.SearchResultElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchApi: SearchApi,
    private val genreDao: GenreDao
) : ViewModel() {

    private val _searchResult = MutableStateFlow(listOf<SearchResultElement>())
    val searchResult = _searchResult.asStateFlow()

    private val _apiResult = MutableStateFlow<Result>(Result.Idle)
    val apiResult = _apiResult.asStateFlow()

    private val _genres: MutableList<GenreEntity> = mutableListOf()


    init {
        getAllGenres()
    }

    fun getSearchResults(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    searchApi.getSearchResults(query = query)
                },
                onDataReady = {
                    _searchResult.value = it.results
                }
            ).collect(_apiResult)
        }
    }

    private fun getAllGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            genreDao.observeGenres().collect {
                _genres.addAll(it)
            }
        }
    }

    fun getRelatedGenres(genreIds: List<Int>): List<String> {
        val genreNames = mutableListOf<String>()
        genreIds.forEach { genreId ->
            genreNames.add(_genres.find {
                it.genreId == genreId
            }?.genreName ?: "")
        }
        return genreNames
    }
}