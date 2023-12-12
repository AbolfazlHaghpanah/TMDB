package com.hooshang.tmdb.feature.home.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.Result
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackBarManager: SnackBarManager,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<HomeAction, HomeState>() {
    init {
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }



    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Refresh -> {
                tryAgainApi()
            }

            else -> {}
        }
    }

    override fun setInitialState(): HomeState {
        return HomeState()
    }

    private fun observeNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.observeNowPlayingUseCase()
                .catch {
                    sendDataBaseError(it)
                }.collect { movies ->
                    setState { copy(nowPlayingMovies = movies) }
                }
        }
        fetchNowPlaying()
    }

    private fun observePopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.observePopularUseCase()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    setState { copy(popularMovies = movies) }
                }
        }
        fetchPopular()
    }

    private fun observeTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.observeTopUseCase()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    setState { copy(topRatedMovies = movies) }
                }
        }
        fetchTopMovies()
    }

    private fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchNowPlayingUseCase()
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is Error -> {
                        setState { copy(isLoading = false) }
                        sendNetworkError(result.message)
                    }

                    is Success<*> -> {
                        setState { copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun fetchPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchPopularMovieUseCase()
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is Error -> {
                        setState { copy(isLoading = false) }
                        sendNetworkError(result.message)
                    }

                    is Success<*> -> {
                        setState { copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun fetchTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchTopMovieUseCase()
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is Error -> {
                        setState { copy(isLoading = false) }
                        sendNetworkError(result.message)
                    }

                    is Success<*> -> {
                        setState { copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun getGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                homeUseCase.fetchGenresUseCase()
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is Error -> {
                        setState { copy(isLoading = false) }
                        sendNetworkError(result.message)
                    }

                    is Success<*> -> {
                        setState { copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private suspend fun sendDataBaseError(
        throwable: Throwable
    ) {
        snackBarManager.sendMessage(
            snackBarMassage = SnackBarMassage(
                snackBarMessage = databaseErrorCatchMessage(throwable),
                snackBarActionLabel = "Try Again",
                snackBarAction = { tryAgainDataBase() },
                snackBarDuration = SnackbarDuration.Indefinite
            )
        )
    }

    private suspend fun sendNetworkError(
        error: String
    ) {
        snackBarManager.sendMessage(
            SnackBarMassage(
                snackBarMessage = error,
                snackBarActionLabel = null,
                snackBarAction = null,
                snackBarDuration = SnackbarDuration.Short
            )
        )
    }

    private fun tryAgainDataBase() {
        observeNowPlaying()
        observePopularMovies()
        observeTopMovies()
    }

    private fun tryAgainApi() {
        getGenre()
        fetchPopular()
        fetchNowPlaying()
        fetchPopular()
        fetchTopMovies()
    }
}