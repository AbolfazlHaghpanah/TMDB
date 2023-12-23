package com.hooshang.tmdb.feature.search.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.Result
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.StringResWrapper
import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.domain.use_case.SearchUseCase
import com.hooshang.tmdb.feature.search.ui.contracts.SearchAction
import com.hooshang.tmdb.feature.search.ui.contracts.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<SearchAction, SearchState>() {

    private var currentSearchString: String = ""

    override fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearch -> search(action.input)
            else -> {}
        }
    }

    override fun setInitialState(): SearchState {
        return SearchState()
    }

    private fun search(value: String) {
        currentSearchString = value
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                searchUseCase(currentSearchString)
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
                }

                is Result.Error -> {
                    snackBarManager.sendMessage(
                        SnackBarMassage(
                            snackBarMessage = result.message,
                            snackBarAction = {
                                search(currentSearchString)
                            },
                            snackBarActionLabel = StringResWrapper(R.string.try_again),
                            snackBarDuration = SnackbarDuration.Indefinite
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
}