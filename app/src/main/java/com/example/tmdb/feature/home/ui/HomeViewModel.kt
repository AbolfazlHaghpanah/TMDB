package com.example.tmdb.feature.home.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.utils.databaseErrorCatchMessage
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.Result.Success
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.feature.home.data.local.dao.HomeDao
import com.example.tmdb.feature.home.data.local.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.local.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.data.remote.HomeApi
import com.example.tmdb.feature.home.data.remote.json.GenreResponse
import com.example.tmdb.feature.home.data.remote.json.MovieResponse
import com.example.tmdb.feature.home.data.repository.HomeRepository
import com.example.tmdb.feature.home.ui.model.HomeMovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackBarManager: SnackBarManager,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _nowPlayingMovies =
        MutableStateFlow<List<HomeMovieUiModel>>(emptyList())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies =
        MutableStateFlow<List<HomeMovieUiModel>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topMovies =
        MutableStateFlow<List<HomeMovieUiModel>>(emptyList())
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
            homeRepository.observeNowPlaying().collect { movies ->
                _nowPlayingMovies.emit(
                    movies.map { it }
                )
            }
        }
        getNowPlaying()
    }

    private fun observePopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            homeRepository.observePopularMovie().collect {
                _popularMovies.emit(it)
            }
        }
        getPopular()
    }

    private fun observeTopMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            homeRepository.observeTopMovie().collect {
                _topMovies.emit(it)
            }
        }
        getTopMovies()
    }

    private fun getNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.fetchNowPlaying()
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.fetchPopularMovie()
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.fetchTopMovie()
        }

    }

    private fun getGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.fetchGenres()
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