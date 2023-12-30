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
import com.hooshang.tmdb.feature.home.domain.use_case.FetchGenresUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchNowPlayingUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchPopularMovieUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchTopMovieUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.ObserveNowPlayingUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.ObservePopularUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.ObserveTopUseCase
import com.hooshang.tmdb.feature.home.ui.contracts.HomeAction
import com.hooshang.tmdb.feature.home.ui.contracts.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackBarManager: SnackBarManager,
    private val fetchGenresUseCase: FetchGenresUseCase,
    private val fetchNowPlayingUseCase: FetchNowPlayingUseCase,
    private val fetchPopularMovieUseCase: FetchPopularMovieUseCase,
    private val fetchTopMovieUseCase: FetchTopMovieUseCase,
    private val observeNowPlayingUseCase: ObserveNowPlayingUseCase,
    private val observePopularUseCase: ObservePopularUseCase,
    private val observeTopUseCase: ObserveTopUseCase
) : BaseViewModel<HomeAction, HomeState>() {
    init {
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
        fetchNowPlaying()
        fetchPopular()
        fetchTopMovies()
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
            observeNowPlayingUseCase()
                .distinctUntilChanged()
                .catch {
                    sendDataBaseError(it)
                }.collect { movies ->
                    setState { copy(nowPlayingMovies = movies) }
                }
        }
    }

    private fun observePopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            observePopularUseCase()
                .distinctUntilChanged()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    setState { copy(popularMovies = movies) }
                }
        }
    }

    private fun observeTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            observeTopUseCase()
                .distinctUntilChanged()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    setState { copy(topRatedMovies = movies) }
                }
        }
    }

    private fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            resultWrapper {
                fetchNowPlayingUseCase()
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
                fetchPopularMovieUseCase()
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
                fetchTopMovieUseCase()
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
                fetchGenresUseCase()
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
                snackBarDuration = SnackbarDuration.Short
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

    private fun tryAgainApi() {
        getGenre()
        fetchPopular()
        fetchNowPlaying()
        fetchPopular()
        fetchTopMovies()
    }
}