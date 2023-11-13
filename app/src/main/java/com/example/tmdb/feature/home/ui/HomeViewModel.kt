package com.example.tmdb.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.home.data.genre.dao.GenreDao
import com.example.tmdb.feature.home.data.movie.dao.MovieDao
import com.example.tmdb.feature.home.data.movie.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.relation.PopularMovieWithGenre
import com.example.tmdb.feature.home.data.relation.TopMovieWithGenre
import com.example.tmdb.feature.home.data.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.network.HomeApi
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

    private val _popularMovies = MutableStateFlow<List<PopularMovieWithGenre>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topMovies = MutableStateFlow<List<TopMovieWithGenre>>(emptyList())
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
            movieDao.observePopularMoviesWithGenre().collect(
                _popularMovies
            )

        }
        getPopular()
    }

    private fun observeTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.observeTopMovieWithGenre().collect(_topMovies)
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
                    viewModelScope.launch(Dispatchers.IO) {
                        it.results.forEach { movie ->
                            movieDao.addNowPlayingMovie(movie.toNowPlayingEntity())
                        }
                    }
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
                    viewModelScope.launch(Dispatchers.IO) {
                        it.results.forEach { movie ->

                            movieDao.addPopularMovie(movie.toPopularMovieEntity())

                            movie.genreIds.forEach {
                                movieDao.addPopularMoviesGenre(PopularMovieGenreCrossRef(movie.id, it))
                            }
                        }
                    }
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
                    viewModelScope.launch(Dispatchers.IO) {
                        it.results.forEach { movie ->

                            movieDao.addTopMovie(movie.toTopPlayingEntity())

                            movie.genreIds.forEach {
                                movieDao.addTopMoviesGenre(TopMovieGenreCrossRef(movie.id, it))
                            }
                        }
                    }
                }
            ).collect(_result)
        }
    }

    private fun getGenre(){
        viewModelScope.launch(Dispatchers.IO){
            safeApi(
                call = {
                    homeApi.getGenre()
                },
                onDataReady = {
                    viewModelScope.launch (Dispatchers.IO){
                        it.genres.forEach {
                            genreDao.addGenre(it.toGenreEntity())
                        }
                    }
                }
            ).collect(_result)
        }
    }
}