package com.hooshang.tmdb.feature.detail.ui

import android.util.Log
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<DetailsAction, DetailsState>() {

    private val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0

    init {
        observeDetailMovieWithAllRelations()
    }

    override fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.AddToFavorite -> addToFavorite()
            is DetailsAction.RemoveFromFavorite -> removeFromFavorite()
            else -> {}
        }
    }

    override fun setInitialState(): DetailsState {
        return DetailsState()
    }

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            detailUseCase.observeDetailUseCase(id)
                .catch {
                    sendDataBaseError(
                        throwable = it,
                        onTryAgain = { observeDetailMovieWithAllRelations() })
                }
                .distinctUntilChanged()
                .collectLatest { domainModel ->
                    Log.d(
                        "asd",
                        "$domainModel " +
                                " "
                    )
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
                        sendNetworkError(result.message)
                    }
                }
            }
        }
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

    private suspend fun sendDataBaseError(
        throwable: Throwable,
        onTryAgain: () -> Unit
    ) {
        snackBarManager.sendMessage(
            snackBarMassage = SnackBarMassage(
                snackBarMessage = databaseErrorCatchMessage(throwable),
                snackBarActionLabel = "Try Again",
                snackBarAction = onTryAgain,
                snackBarDuration = SnackbarDuration.Short
            )
        )
    }

    private suspend fun sendNetworkError(
        message: String?
    ) {
        snackBarManager.sendMessage(
            SnackBarMassage(
                snackBarMessage = message,
                snackBarAction = { fetchMovieDetail() },
                snackBarActionLabel = "try again"
            )
        )
    }

    private fun MovieDetailDomainModel.toDetailState(): DetailsState {
        return DetailsState(
            movie = this,
            isLoading = false
        )
    }
}
