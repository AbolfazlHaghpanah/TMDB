package com.example.tmdb.feature.home.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.databaseErrorCatchMessage
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.moviedata.Dao.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.Result.Success
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.network.HomeApi
import com.example.tmdb.feature.home.network.json.GenreResponse
import com.example.tmdb.feature.home.network.json.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeApi: HomeApi,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val _nowPlayingMovies =
        MutableStateFlow<List<MovieWithGenreDatabaseWrapper>>(emptyList())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies =
        MutableStateFlow<List<MovieWithGenreDatabaseWrapper>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topMovies =
        MutableStateFlow<List<MovieWithGenreDatabaseWrapper>>(emptyList())
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

    private suspend fun dismissSnackBar() {
        _snackBarMassage.emit(
            _snackBarMassage.value?.copy(isHaveToShow = false)
        )
    }

    private fun observeNowPlaying() {

        viewModelScope.launch(Dispatchers.IO) {

            movieDao.observeNowPlayingMovie()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    _nowPlayingMovies.emit(
                        movies.map { it.toMovieDataWrapper() }
                    )
                }
        }
        getNowPlaying()
    }

    private fun observePopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            movieDao.observePopularMovie()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    _popularMovies.emit(
                        movies.map { it.toMovieDataWrapper() }
                    )
                }
        }
        getPopular()
    }

    private fun observeTopMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            movieDao.observeTopMovie()
                .catch {
                    sendDataBaseError(it)
                }
                .collect { movies ->
                    _topMovies.emit(
                        movies.map { it.toMovieDataWrapper() }
                    )
                }
        }
        getTopMovies()
    }

    private fun getNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getNowPlaying()
                }
            ).collect {
                storeNowPlaying(it)
            }
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getMostPopular()
                }
            ).collect {
                storePopulars(it)
            }
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getTopRated()
                }
            ).collect {
                storeTopMovie(it)
            }
        }

    }

    private fun getGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getGenre()
                }
            ).collect {
                storeGenre(it)
            }
        }
    }

    private suspend fun storeGenre(result: Result) {

        when (result) {
            is Success<*> -> {

                dismissSnackBar()
                val data = result.response as GenreResponse

                data.genres.forEach { it ->
                    try {
                        genreDao.addGenre(it.toGenreEntity())
                    } catch (t: Throwable) {
                        sendDataBaseError(t)
                    }
                }
            }

            is Result.Error -> {
                val error = (result).message
                sendNetworkError(error)
            }

            else -> {}
        }

    }

    private suspend fun storeNowPlaying(result: Result) {

        when (result) {

            is Success<*> -> {
                dismissSnackBar()
                val data = result.response as MovieResponse
                try {
                    data.results.forEach { movie ->
                        movieDao.addNowPlayingMovie(
                            movie.toNowPlayingEntity(),
                            movie.toMovieEntity()
                        )
                    }
                } catch (t: Throwable) {
                    sendDataBaseError(t)
                }
            }

            is Result.Error -> {
                val error = result.message
                sendNetworkError(error)
            }

            else -> {}
        }

    }

    private suspend fun storePopulars(result: Result) {

        when (result) {

            is Success<*> -> {
                dismissSnackBar()
                val data = result.response as MovieResponse
                try {
                    data.results.forEach { movie ->
                        movieDao.addPopularMovie(movie)
                    }
                } catch (t: Throwable) {
                    sendDataBaseError(t)
                }
            }

            is Result.Error -> {
                val error = result.message
                sendNetworkError(error)
            }

            else -> {}
        }

    }

    private suspend fun storeTopMovie(result: Result) {

        when (result) {
            is Success<*> -> {
                dismissSnackBar()
                val data = result.response as MovieResponse
                try {
                    data.results.forEach { movie ->
                        movieDao.addTopMovie(
                            movie
                        )
                    }
                } catch (t: Throwable) {
                    sendDataBaseError(t)
                }
            }

            is Result.Error -> {
                val error = result.message
                sendNetworkError(error)
            }

            else -> {}
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
}