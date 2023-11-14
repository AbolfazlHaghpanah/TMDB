package com.example.tmdb.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.genre.dao.GenreDao
import com.example.tmdb.feature.home.data.movie.dao.MovieDao
import com.example.tmdb.feature.home.data.movie.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef
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

    private val _nowPlayingMovies = MutableStateFlow<List<NowPlayingEntity>>(emptyList())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<List<MovieWithGenreDatabaseWrapper>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topMovies = MutableStateFlow<List<MovieWithGenreDatabaseWrapper>>(emptyList())
    val topMovies = _topMovies.asStateFlow()

    private val _result = MutableStateFlow<Result>(Result.Idle)
    val result = _result.asStateFlow()

    init {
        getGenre()
        observeNowPlaying()
        observePopularMovies()
        observeTopMovies()
    }

    private fun observeNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeNowPlayingMovies().collect(
                _nowPlayingMovies
            )
        }
        getNowPlaying()
    }

    private fun observePopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observePopularMoviesWithGenre().collect {
                _popularMovies.emit(
                    it.map { it.toWrapper() }
                )
            }
        }
        getPopular()
    }

    private fun observeTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeTopMovieWithGenre().collect {
                _topMovies.emit(
                    it.map { it.toWrapper() }
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
                onDataReady = {
                    storeNowPlaying(it)
                }
            ).collect(_result)
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
            ).collect(_result)
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
            ).collect(_result)
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
            ).collect(_result)
        }
    }

    private fun storeGenres(genre : GenreResponse){
        viewModelScope.launch(Dispatchers.IO) {
            genre.genres.forEach {
                genreDao.addGenre(it.toGenreEntity())
            }
        }
    }

    private fun storeNowPlaying(movies : MovieResponse){
        viewModelScope.launch(Dispatchers.IO) {
            movies.results.forEach { movie ->
                movieDao.addNowPlayingMovie(movie.toNowPlayingEntity())
            }
        }
    }

    private fun storePopulars(movie : MovieResponse){
        viewModelScope.launch(Dispatchers.IO) {
            movie.results.forEach { movie ->

                movieDao.addPopularMovie(movie.toPopularMovieEntity())

                movie.genreIds.forEach {
                    movieDao.addPopularMoviesGenre(
                        PopularMovieGenreCrossRef(
                            movieId = movie.id,
                            genreId = it
                        )
                    )
                }
            }
        }
    }

    private fun storeTopMovie(movie : MovieResponse){
        viewModelScope.launch(Dispatchers.IO) {
            movie.results.forEach { movie ->

                movieDao.addTopMovie(movie.toTopPlayingEntity())

                movie.genreIds.forEach {
                    movieDao.addTopMoviesGenre(
                        TopMovieGenreCrossRef(
                            movieId = movie.id,
                            genreId = it
                        )
                    )
                }
            }
        }
    }
}