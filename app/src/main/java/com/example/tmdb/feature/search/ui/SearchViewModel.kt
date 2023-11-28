package com.example.tmdb.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.feature.search.network.SearchApi
import com.example.tmdb.feature.search.network.json.SearchResult
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
    private val genreDao: GenreDao,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val _searchResult = MutableStateFlow(listOf<SearchResultElement>())
    val searchResult = _searchResult.asStateFlow()

    private val _apiResult = MutableStateFlow<Result?>(null)
    val apiResult = _apiResult.asStateFlow()

    private val _genres: MutableList<GenreEntity> = mutableListOf()

    private val _snackBarMessage = MutableStateFlow<SnackBarMassage?>(null)

    private var _currentSearchString: String = ""

    init {
        getAllGenres()
    }

    fun getSearchResults(query: String) {
        _currentSearchString = query
        viewModelScope.launch(Dispatchers.IO) {
            dismissSnackBar()
            safeApi(
                call = {
                    searchApi.getSearchResults(query = query)
                }
            ).collect {
                emiSearchResult(it)
            }
        }
    }

    private fun getAllGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            genreDao.observeGenres().collect {
                _genres.addAll(it)
            }
        }
    }

    fun getRelatedGenres(genreIds: List<Int>): String {
        val genreNames = mutableListOf<String>()
        genreIds.forEach { genreId ->
            genreNames.add(_genres.find {
                it.genreId == genreId
            }?.genreName ?: "")
        }
        return genreNames.joinToString(separator = "  |  ") { it }
    }

    private suspend fun emiSearchResult(result: Result) {
        viewModelScope.launch {
            when (result) {
                is Result.Success<*> -> {
                    val data = result.response as SearchResult
                    _searchResult.emit(data.results)
                    _apiResult.emit(result)
                }

                is Result.Error -> {
                    val data = result.message
                    _snackBarMessage.emit(
                        SnackBarMassage(
                            snackBarMessage = data,
                            snackBarAction = {
                                getSearchResults(_currentSearchString)
                            },
                            isHaveToShow = true,
                            snackBarActionLabel = "try again"
                        )
                    )
                    _apiResult.emit(result)
                }

                is Result.Loading -> {
                    _apiResult.emit(result)
                }
            }
        }
    }

    suspend fun showLastSnackBar() {
        snackBarManager.sendMessage(
            _snackBarMessage.value
        )
    }

    private suspend fun dismissSnackBar() {
        _snackBarMessage.emit(
            _snackBarMessage.value?.copy(isHaveToShow = false)
        )
    }

}