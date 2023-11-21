package com.example.tmdb.feature.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.Result.Success
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.LocalSnackbarHostState
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.network.HomeApi
import com.example.tmdb.feature.home.network.json.GenreResponse
import com.example.tmdb.feature.home.network.json.GenresResponse
import com.example.tmdb.feature.home.network.json.MovieResponse
import com.example.tmdb.feature.home.network.json.MovieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeApi: HomeApi,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
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

    private val _errorMessage = Channel<String?>(
    )
    val errorMessage = _errorMessage.receiveAsFlow()

    init {
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }

    fun tryAgainApi() {
        viewModelScope.launch {
            _errorMessage.send(null)
        }
        getGenre()
        getNowPlaying()
        getPopular()
        getTopMovies()
    }


    private fun observeNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeNowPlayingMovie().catch { }.collect {
                _nowPlayingMovies.emit(it.map { it.toMovieDataWrapper() })
            }
        }
        getNowPlaying()
    }

    private fun observePopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observePopularMovie().collect {
                _popularMovies.emit(
                    it.map { it.toMovieDataWrapper() }
                )
            }
        }
        getPopular()
    }

    private fun observeTopMovies() {

        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeTopMovie().collect {
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
                        genreDao.addGenre(it.toGenreEntity())
                    }
                }

                is Result.Error -> {
                    val error = (_genreResult.value as Result.Error).message

                    _errorMessage.send(
                        error
                    )
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
                    data.results.forEach { movie ->
                        movieDao.addNowPlayingMovie(
                            movie.toNowPlayingEntity(),
                            movie.toMovieEntity()
                        )
                    }
                }

                is Result.Error -> {
                    val error = (_nowPlayingResult.value as Result.Error).message
                    _errorMessage.send(
                        error
                    )
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
                    data.results.forEach { movie ->
                        movieDao.addPopularMovie(movie)
                    }
                }

                is Result.Error -> {
                    val error = (_popularMovieResult.value as Result.Error).message
                    _errorMessage.send(
                        error
                    )
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
                    data.results.forEach { movie ->
                        movieDao.addTopMovie(
                            movie
                        )
                    }
                }

                is Result.Error -> {
                    val error = (_topMovieResult.value as Result.Error).message
                    _errorMessage.send(
                        error
                    )
                }

                else -> {}
            }
        }
    }
}