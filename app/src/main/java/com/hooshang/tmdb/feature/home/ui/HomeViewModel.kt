package com.hooshang.tmdb.feature.home.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.Result.Error
import com.hooshang.tmdb.core.utils.Result.Success
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.databaseErrorCatchMessage
import com.hooshang.tmdb.feature.home.domain.use_case.HomeUseCase
import com.hooshang.tmdb.feature.home.ui.contracts.HomeAction
import com.hooshang.tmdb.feature.home.ui.contracts.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackBarManager: SnackBarManager,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<HomeAction, HomeState>() {

    private val _snackBarMassage = MutableStateFlow<SnackBarMassage?>(null)

    init {
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ShowLastSnackBar -> {
                viewModelScope.launch {
                    showLastSnackBar()
                }
            }
            else -> {}
        }
    }

    override fun setInitialState(): HomeState {
        return HomeState()
    }

    private suspend fun showLastSnackBar() {
        snackBarManager.sendMessage(
            _snackBarMassage.value
        )
    }

    private fun observeNowPlaying() {

        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.getNowPlayingUseCase()
                .catch {
                    sendDataBaseError(it)
                }.collect { movies ->
                    setState { copy(nowPlayingMovies = movies) }
                }

        }
        getNowPlaying()
    }

    private fun observePopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            homeUseCase.getPopularUseCase()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    setState { copy(popularMovies = movies) }
                }
        }
        getPopular()
    }

    private fun observeTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.getTopUseCase()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    setState { copy(topRatedMovies = movies) }
                }
        }
        getTopMovies()
    }

    private fun getNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchNowPlayingUseCase()
            }.collect { result ->
                if (result is Error) sendNetworkError(result.message)
                else if (result is Success<*>) dismissSnackBar()
            }
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchPopularMovieUseCase()
            }.collect { result ->
                if (result is Error) sendNetworkError(result.message)
                else if (result is Success<*>) dismissSnackBar()
            }
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchTopMovieUseCase()
            }.collect { result ->
                if (result is Error) sendNetworkError(result.message)
                else if (result is Success<*>) dismissSnackBar()
            }
        }
    }

    private fun getGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchGenresUseCase()
            }.collect { result ->
                if (result is Error) sendNetworkError(result.message)
                else if (result is Success<*>) dismissSnackBar()
            }
        }
    }

    private suspend fun sendDataBaseError(
        throwable: Throwable
    ) {
        _snackBarMassage.emit(
            SnackBarMassage(
                snackBarMessage = databaseErrorCatchMessage(throwable),
                snackBarActionLabel = "Try Again",
                snackBarAction = { tryAgainApi() },
                snackBarDuration = SnackbarDuration.Short
            )
        )
        snackBarManager.sendMessage(
            snackBarMassage = _snackBarMassage.value
        )
    }

    private suspend fun sendNetworkError(
        error: String
    ) {
        _snackBarMassage.emit(
            SnackBarMassage(
                snackBarMessage = error,
                snackBarActionLabel = "Try Again",
                snackBarAction = { tryAgainApi() },
                snackBarDuration = SnackbarDuration.Short
            )
        )
        snackBarManager.sendMessage(
            _snackBarMassage.value
        )
    }

    private fun tryAgainApi() {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
        }
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }

    private fun dismissSnackBar() {
        viewModelScope.launch {
            _snackBarMassage.emit(
                _snackBarMassage.value?.copy(shouldShow = false)
            )
        }
    }
}