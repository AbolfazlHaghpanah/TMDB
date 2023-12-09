package com.hooshang.tmdb.feature.search.ui

import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.Result
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.domain.use_case.SearchUseCase
import com.hooshang.tmdb.feature.search.ui.contracts.SearchAction
import com.hooshang.tmdb.feature.search.ui.contracts.SearchEffect
import com.hooshang.tmdb.feature.search.ui.contracts.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<SearchAction, SearchState, SearchEffect>() {

    private val _snackBarMessage = MutableStateFlow<SnackBarMassage?>(null)

    private var _currentSearchString: String = ""

    override fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearch -> search(action.input)

            is SearchAction.ShowLastSnackBar -> {
                viewModelScope.launch {
                    showLastSnackBar()
                }
            }

            else -> {}
        }
    }

    override fun setInitialState(): SearchState {
        return SearchState()
    }

    private fun search(value: String) {
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
                    setState { copy(isLoading = false, searchResults = data.toPersistentList()) }
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
                    setState { copy(isLoading = false, isError = true) }
                }

                is Result.Loading -> {
                    setState { copy(isLoading = true) }
                }
            }
        }
    }

    private suspend fun showLastSnackBar() {
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