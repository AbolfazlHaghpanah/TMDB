package com.hooshang.tmdb.feature.detail.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.Result
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.StringResWrapper
import com.hooshang.tmdb.core.utils.databaseErrorCatchMessage
import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.usecase.AddFavoriteUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.FetchDetailUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.ObserveDetailUseCase
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsAction
import com.hooshang.tmdb.feature.detail.ui.contracts.DetailsState
import com.hooshang.tmdb.feature.favorite.domain.use_case.DeleteFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val fetchDetailUseCase: FetchDetailUseCase,
    private val observeDetailUseCase: ObserveDetailUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<DetailsAction, DetailsState>() {

    private val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0

    init {
        observeDetailMovieWithAllRelations()
        fetchMovieDetail()
    }

    override fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.AddToFavorite -> addToFavorite()
            is DetailsAction.RemoveFromFavorite -> removeFromFavorite()
            else -> {}
        }
    }

    override fun setInitialState(): DetailsState = DetailsState()

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            observeDetailUseCase(id)
                .catch {
                    snackBarManager.sendMessage(
                        snackBarMassage = SnackBarMassage(
                            snackBarMessage = databaseErrorCatchMessage(it),
                            snackBarActionLabel = StringResWrapper(R.string.label_try_again),
                            snackBarAction = { observeDetailMovieWithAllRelations() },
                            snackBarDuration = SnackbarDuration.Short
                        )
                    )
                }
                .distinctUntilChanged()
                .collect { domainModel ->
                    setState { domainModel.toDetailState() }
                }
        }
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
            resultWrapper {
                fetchDetailUseCase(id)
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
                        snackBarManager.sendMessage(
                            SnackBarMassage(
                                snackBarMessage = result.message,
                                snackBarAction = { fetchMovieDetail() },
                                snackBarActionLabel = StringResWrapper(R.string.label_try_again)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun addToFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addFavoriteUseCase(
                    state.value.movie.id,
                    state.value.movie.genres.map { it.first }
                )
            } catch (t: Throwable) {
                snackBarManager.sendMessage(
                    snackBarMassage = SnackBarMassage(
                        snackBarMessage = databaseErrorCatchMessage(t),
                        snackBarActionLabel = StringResWrapper(R.string.label_try_again),
                        snackBarAction = { addToFavorite() },
                        snackBarDuration = SnackbarDuration.Short
                    )
                )
            }
        }
    }

    private fun removeFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteFromFavoriteUseCase(id)
            } catch (t: Throwable) {
                snackBarManager.sendMessage(
                    snackBarMassage = SnackBarMassage(
                        snackBarMessage = databaseErrorCatchMessage(t),
                        snackBarActionLabel = StringResWrapper(R.string.label_try_again),
                        snackBarAction = { removeFromFavorite() },
                        snackBarDuration = SnackbarDuration.Short
                    )
                )
            }
        }
    }

    private fun MovieDetailDomainModel.toDetailState(): DetailsState =
        DetailsState(
            movie = this,
            isLoading = false
        )
}
