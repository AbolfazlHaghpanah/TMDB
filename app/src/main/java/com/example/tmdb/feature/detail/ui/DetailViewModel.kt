package com.example.tmdb.feature.detail.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.ui.resultWrapper
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.core.utils.databaseErrorCatchMessage
import com.example.tmdb.feature.detail.domain.model.MovieDetail
import com.example.tmdb.feature.detail.domain.usecase.DetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private var _movieDetail: MutableStateFlow<MovieDetail?> =
        MutableStateFlow(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<SnackBarMassage?>(null)

    init {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
        }

        observeDetailMovieWithAllRelations()
        fetchMovieDetail()
    }

    fun addToFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDetail.value?.let { detailMovieWithAllRelations ->
                    detailMovieWithAllRelations.genres.let { genreEntities ->
                        detailUseCase.addFavoriteUseCase(
                            movieId = detailMovieWithAllRelations.id,
                            genres = genreEntities.map { it.first }
                        )
                    }
                }
            } catch (t: Throwable) {
                sendDataBaseError(throwable = t, onTryAgain = {
                    addToFavorite()
                })
            }
        }
    }

    fun removeFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDetail.value?.let { detailUseCase.removeFavoriteUseCase(it) }
            } catch (t: Throwable) {
                sendDataBaseError(throwable = t, onTryAgain = {
                    removeFromFavorite()
                })
            }
        }
    }

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            detailUseCase.observeDetailUseCase(id)
                .catch {
                    sendDataBaseError(throwable = it, onTryAgain = {
                        observeDetailMovieWithAllRelations()
                    })
                }
                .collect {
                    _movieDetail.emit(it)
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
                    is Result.Success<*> -> {
                        _isLoading.emit(false)
                    }

                    is Result.Error -> {
                        _isLoading.emit(false)
                        val error = result.message
                        _snackBarMessage.emit(
                            SnackBarMassage(
                                snackBarMessage = error,
                                isHaveToShow = true,
                                snackBarAction = {
                                    fetchMovieDetail()
                                },
                                snackBarActionLabel = "try again"
                            )
                        )
                        snackBarManager.sendMessage(_snackBarMessage.value)
                    }

                    else -> {}
                }
            }
        }
    }

    suspend fun showLastSnackBar() {
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
}
