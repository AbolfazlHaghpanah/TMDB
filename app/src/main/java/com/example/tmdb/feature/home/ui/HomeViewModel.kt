package com.example.tmdb.feature.home.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.utils.Result.Error
import com.example.tmdb.core.utils.Result.Success
import com.example.tmdb.core.utils.resultWrapper
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.core.utils.databaseErrorCatchMessage
import com.example.tmdb.feature.home.domain.model.HomeMovieDomainModel
import com.example.tmdb.feature.home.domain.use_case.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackBarManager: SnackBarManager,
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _nowPlayingMovies =
        MutableStateFlow<List<HomeMovieDomainModel>>(emptyList())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies =
        MutableStateFlow<List<HomeMovieDomainModel>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topMovies =
        MutableStateFlow<List<HomeMovieDomainModel>>(emptyList())
    val topMovies = _topMovies.asStateFlow()

    private val _snackBarMassage = MutableStateFlow<SnackBarMassage?>(null)

    init {
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }

    suspend fun showLastSnackBar() {
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
                    _nowPlayingMovies.emit(
                        movies.map { it }
                    )
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
                .collect {
                    _popularMovies.emit(it)
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
                .collect {
                    _topMovies.emit(it)
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
                _snackBarMassage.value?.copy(isHaveToShow = false)
            )
        }
    }
}