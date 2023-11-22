package com.example.tmdb.feature.home.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.databaseErrorCatchMessage
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.Result.Success
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.SnackBarMessage
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
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
    private val snackBarMessage: SnackBarMessage
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

    private val _nowPlayingResult = MutableStateFlow<Result>(Result.Idle)

    private val _popularMovieResult = MutableStateFlow<Result>(Result.Idle)

    private val _topMovieResult = MutableStateFlow<Result>(Result.Idle)

    private val _genreResult = MutableStateFlow<Result>(Result.Idle)

    init {
        viewModelScope.launch {
            snackBarMessage.dismissSnackBar()
        }
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }



    private fun observeNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {

            movieDao.observeNowPlayingMovie()
                .catch {

                }.collect {
                    _nowPlayingMovies.emit(
                        it.map { it.toMovieDataWrapper() }
                    )
                }
        }
        getNowPlaying()
    }

    private fun observePopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            movieDao.observePopularMovie().catch {
                sendDataBaseError(it)
            }.collect {

                _popularMovies.emit(
                    it.map { it.toMovieDataWrapper() }
                )
            }
        }
        getPopular()
    }

    private fun observeTopMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            movieDao.observeTopMovie().catch {
                sendDataBaseError(it)
            }.collect {
                _topMovies.emit(
                    it.map { it.toMovieDataWrapper() }
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
                },
                onRequestDone = {
                    storeNowPlaying()
                }
            ).collect(_nowPlayingResult)
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getMostPopular()
                },
                onRequestDone = {
                    storePopulars()
                }
            ).collect(_popularMovieResult)
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getTopRated()
                },
                onRequestDone = {
                    storeTopMovie()
                }
            ).collect(_topMovieResult)
        }

    }

    private fun getGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getGenre()
                },
                onRequestDone = {
                    storeGenres()
                }
            ).collect(_genreResult)
        }
    }


    private fun storeGenres() {
        viewModelScope.launch(Dispatchers.IO) {

            when (_genreResult.value) {

                is Success<*> -> {
                    val data = (_genreResult.value as Success<*>).response as GenreResponse
                    data.genres.forEach { it ->
                        try {
                            genreDao.addGenre(it.toGenreEntity())
                        } catch (t: Throwable) {
                            sendDataBaseError(t)
                        }
                    }
                }

                is Result.Error -> {
                    val error = (_genreResult.value as Result.Error).message
                    sendNetworkError(error)
                }

                else -> {}
            }
        }
    }

    private fun storeNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            when (_nowPlayingResult.value) {

                is Success<*> -> {
                    val data = (_nowPlayingResult.value as Success<*>).response as MovieResponse
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
                    val error = (_nowPlayingResult.value as Result.Error).message
                    sendNetworkError(error)
                }

                else -> {}
            }
        }
    }

    private fun storePopulars() {
        viewModelScope.launch(Dispatchers.IO) {

            when (_popularMovieResult.value) {
                is Success<*> -> {
                    val data = (_popularMovieResult.value as Success<*>).response as MovieResponse
                    try {
                        data.results.forEach { movie ->
                            movieDao.addPopularMovie(movie)
                        }
                    } catch (t: Throwable) {
                        sendDataBaseError(t)
                    }
                }

                is Result.Error -> {
                    val error = (_popularMovieResult.value as Result.Error).message
                    sendNetworkError(error)
                }

                else -> {}
            }
        }
    }

    private fun storeTopMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            when (_topMovieResult.value) {
                is Success<*> -> {
                    val data = (_topMovieResult.value as Success<*>).response as MovieResponse
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
                    val error = (_topMovieResult.value as Result.Error).message
                    sendNetworkError(error)
                }

                else -> {}
            }
        }
    }

    private fun isOneOfListEmpty() =
        _nowPlayingMovies.value.isEmpty() || _popularMovies.value.isEmpty() || _topMovies.value.isEmpty()

    private suspend fun sendDataBaseError(
        throwable: Throwable
    ) {
        snackBarMessage.sendMessage(
            message = databaseErrorCatchMessage(throwable),
            actionLabel = "Try Again",
            action = { tryAgainApi() },
            duration = if (isOneOfListEmpty()) {
                SnackbarDuration.Indefinite
            } else {
                SnackbarDuration.Short
            }
        )
    }

    private suspend fun sendNetworkError(
        error: String
    ) {
        snackBarMessage.sendMessage(
            message = error,
            actionLabel = "Try Again",
            action = { tryAgainApi() },
            duration = if (isOneOfListEmpty()) {
                SnackbarDuration.Indefinite
            } else {
                SnackbarDuration.Short
            }
        )
    }

    private fun tryAgainApi() {
        viewModelScope.launch {
            snackBarMessage.dismissSnackBar()
        }
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }
}