package com.example.tmdb.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.utils.Result
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.core.utils.resultWrapper
import com.example.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.example.tmdb.feature.search.domain.use_case.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val _searchResult = MutableStateFlow<List<SearchMovieWithGenreDomainModel>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    private val _apiResult = MutableStateFlow<Result?>(null)
    val apiResult = _apiResult.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<SnackBarMassage?>(null)

    private var _currentSearchString: String = ""

    fun search(value: String) {
        _currentSearchString = value
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                searchUseCase(_currentSearchString)
            }.collect {
                emiSearchResult(it)
            }
        }
    }

    private suspend fun emiSearchResult(result: Result) {
        viewModelScope.launch {
            when (result) {
                is Result.Success<*> -> {
                    val data = result.response as List<SearchMovieWithGenreDomainModel>
                    _searchResult.emit(data)
                    _apiResult.emit(result)
                    dismissSnackBar()
                }

                is Result.Error -> {
                    val data = result.message
                    _snackBarMessage.emit(
                        SnackBarMassage(
                            snackBarMessage = data,
                            snackBarAction = {
                                search(_currentSearchString)
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