package com.example.tmdb.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.network.HomeApi
import com.example.tmdb.feature.home.network.json.GenreResponse
import com.example.tmdb.feature.home.network.json.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    val nowPlayingResult = _nowPlayingResult.asStateFlow()

    private val _popularMovieResult = MutableStateFlow<Result>(Result.Idle)
    val popularMovieResult = _popularMovieResult.asStateFlow()

    private val _topMovieResult = MutableStateFlow<Result>(Result.Idle)
    val topMovieResult = _topMovieResult.asStateFlow()

    private val _genreResult = MutableStateFlow<Result>(Result.Idle)
    val genreResult = _genreResult.asStateFlow()

    init {
        getGenre()
        observeTopMovies()
        observeNowPlaying()
        observePopularMovies()
    }

    private fun observeNowPlaying() {
        getNowPlaying()
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeNowPlayingMovie().collect {
                _nowPlayingMovies.emit(it.map { it.toMovieDataWrapper() })
            }
        }
    }

    private fun observePopularMovies() {
        getPopular()

        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observePopularMovie().collect {
                _popularMovies.emit(
                    it.map { it.toMovieDataWrapper() }
                )
            }
        }
    }

    private fun observeTopMovies() {
        getTopMovies()

        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeTopMovie().collect {
                _topMovies.emit(
                    it.map { it.toMovieDataWrapper() }
                )
            }
        }
    }

    private fun getNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getNowPlaying()
                },
                onDataReady = {
                    storeNowPlaying(it)
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
                onDataReady = {
                    storePopulars(it)
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
                onDataReady = {
                    storeTopMovie(it)
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
                onDataReady = {
                    storeGenres(it)
                }
            ).collect(_genreResult)
        }
    }

    private fun storeGenres(genre: GenreResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            genre.genres.forEach {
                genreDao.addGenre(it.toGenreEntity())
            }
        }
    }

    private fun storeNowPlaying(movies: MovieResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            movies.results.forEach { movie ->
                movieDao.addNowPlayingMovie(movie.toNowPlayingEntity(), movie.toMovieEntity())
            }
        }
    }

    private fun storePopulars(movie: MovieResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.results.forEach { movie ->
                movieDao.addPopularMovie(movie)
            }
        }
    }

    private fun storeTopMovie(movie: MovieResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.results.forEach { movie ->
                movieDao.addTopMovie(movie)
            }
        }
    }
}