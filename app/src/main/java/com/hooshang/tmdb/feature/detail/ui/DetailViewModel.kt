package com.hooshang.tmdb.feature.detail.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.Result
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.databaseErrorCatchMessage
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.usecase.DetailUseCase
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsAction
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<DetailsAction, DetailsState>() {

    private val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0
    private val _snackBarMessage = MutableStateFlow<SnackBarMassage?>(null)

    init {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
        }
        observeDetailMovieWithAllRelations()
    }

    override fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.AddToFavorite -> addToFavorite()
            is DetailsAction.RemoveFromFavorite -> removeFromFavorite()
            is DetailsAction.ShowLastSnackBar -> viewModelScope.launch { showLastSnackBar() }
            else -> {}
        }
    }

    override fun setInitialState(): DetailsState {
        return DetailsState()
    }

    private fun addToFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                detailUseCase.addFavoriteUseCase(
                    state.value.movie.id,
                    state.value.movie.genres.map { it.first }
                )
            } catch (t: Throwable) {
                sendDataBaseError(
                    throwable = t,
                    onTryAgain = { addToFavorite() })
            }
        }
    }

    private fun removeFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                detailUseCase.removeFavoriteUseCase(id)
            } catch (t: Throwable) {
                sendDataBaseError(
                    throwable = t,
                    onTryAgain = { removeFromFavorite() })
            }
        }
    }

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            detailUseCase.observeDetailUseCase(id)
                .catch {
                    sendDataBaseError(
                        throwable = it,
                        onTryAgain = { observeDetailMovieWithAllRelations() })
                }
                .collect { domainModel ->
                    setState { domainModel.toDetailState() }
                }
        }
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
            resultWrapper {
                detailUseCase.fetchDetailUseCase(id)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is Result.Success<*> -> {
                        setState { copy(isLoading = false) }
                    }

                    is Result.Error -> {
                        setState { copy(isLoading = false) }
                        val error = result.message
                        _snackBarMessage.emit(
                            SnackBarMassage(
                                snackBarMessage = error,
                                shouldShow = true,
                                snackBarAction = {
                                    fetchMovieDetail()
                                },
                                snackBarActionLabel = "try again"
                            )
                        )
                        snackBarManager.sendMessage(_snackBarMessage.value)
                    }
                }
            }
        }
    }

    private suspend fun showLastSnackBar() {
        snackBarManager.sendMessage(
            _snackBarMessage.value
        )
    }

    private suspend fun sendDataBaseError(
        throwable: Throwable,
        onTryAgain: () -> Unit
    ) {
        _snackBarMessage.emit(
            SnackBarMassage(
                snackBarMessage = databaseErrorCatchMessage(throwable),
                snackBarActionLabel = "Try Again",
                snackBarAction = onTryAgain,
                snackBarDuration = SnackbarDuration.Short
            )
        )
        snackBarManager.sendMessage(
            snackBarMassage = _snackBarMessage.value
        )
    }


    private fun MovieDetailDomainModel.toDetailState(): DetailsState {
        return DetailsState(
            movie = this,
            isLoading = false
        )
    }
}
