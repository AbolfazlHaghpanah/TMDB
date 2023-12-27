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
import com.hooshang.tmdb.feature.detail.domain.usecase.AddToFavoriteWithGenresUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.FetchDetailUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.GetDetailUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.ObserveExistInFavoriteUseCase
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
    private val getDetailUseCase: GetDetailUseCase,
    private val fetchDetailUseCase: FetchDetailUseCase,
    private val observeExistInFavoriteUseCase: ObserveExistInFavoriteUseCase,
    private val addToFavoriteWithGenresUseCase: AddToFavoriteWithGenresUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<DetailsAction, DetailsState>() {

    private val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchMovieDetail()
            observeExistInFavorite()
        }
    }

    override fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.AddToFavorite -> addToFavoriteWithGenres()
            is DetailsAction.RemoveFromFavorite -> removeFromFavorite()
            else -> {}
        }
    }

    override fun setInitialState(): DetailsState = DetailsState()

    private suspend fun getMovieDetails() {
        try {
            val movieDetails = getDetailUseCase(id)
            setState { copy(movie = movieDetails) }
        } catch (t: Throwable) {
            snackBarManager.sendMessage(
                snackBarMassage = SnackBarMassage(
                    snackBarMessage = databaseErrorCatchMessage(t),
                    snackBarActionLabel = StringResWrapper(R.string.try_again),
                    snackBarAction = {
                        viewModelScope.launch(Dispatchers.IO) {
                            fetchMovieDetail()
                        }
                    },
                    snackBarDuration = SnackbarDuration.Indefinite
                )
            )
        }
    }

    private suspend fun observeExistInFavorite() {
        observeExistInFavoriteUseCase(id)
            .catch {
                snackBarManager.sendMessage(
                    snackBarMassage = SnackBarMassage(
                        snackBarMessage = databaseErrorCatchMessage(it),
                        snackBarActionLabel = StringResWrapper(R.string.try_again),
                        snackBarAction = {
                            viewModelScope.launch(Dispatchers.IO) {
                                fetchMovieDetail()
                            }
                        },
                        snackBarDuration = SnackbarDuration.Short
                    )
                )
            }
            .distinctUntilChanged()
            .collect {
                setState {
                    copy(movie = state.value.movie.copy(isFavorite = it))
                }
            }

    }

    private suspend fun fetchMovieDetail() {
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
                    getMovieDetails()
                }

                is Result.Error -> {
                    setState { copy(isLoading = false) }
                    snackBarManager.sendMessage(
                        SnackBarMassage(
                            snackBarMessage = result.message,
                            snackBarAction = {
                                viewModelScope.launch(Dispatchers.IO) {
                                    fetchMovieDetail()
                                }
                            },
                            snackBarActionLabel = StringResWrapper(R.string.try_again)
                        )
                    )
                    getMovieDetails()
                }
            }
        }
    }

    private fun addToFavoriteWithGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addToFavoriteWithGenresUseCase(
                    state.value.movie.id,
                    state.value.movie.genres.map { it.first }
                )
            } catch (t: Throwable) {
                snackBarManager.sendMessage(
                    snackBarMassage = SnackBarMassage(
                        snackBarMessage = databaseErrorCatchMessage(t),
                        snackBarActionLabel = StringResWrapper(R.string.try_again),
                        snackBarAction = { addToFavoriteWithGenres() },
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
                        snackBarActionLabel = StringResWrapper(R.string.try_again),
                        snackBarAction = { removeFromFavorite() },
                        snackBarDuration = SnackbarDuration.Short
                    )
                )
            }
        }
    }
}
