package com.example.tmdb.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingDao
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieDao
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovies.TopMovieDao
import com.example.tmdb.feature.home.data.topmovies.TopMovieEntity
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
    private val popularMovieDao: PopularMovieDao,
    private val nowPlayingDao: NowPlayingDao,
    private val topMovieDao: TopMovieDao
) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<List<NowPlayingEntity>>(emptyList())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<List<PopularMovieEntity>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topMovies = MutableStateFlow<List<TopMovieEntity>>(emptyList())
    val topMovies = _topMovies.asStateFlow()

    private val _result = MutableStateFlow<Result>(Result.Idle)
    val result = _result.asStateFlow()

    init {
        observeNowPlaying()
        observeTopMovies()
        observePopularMovies()
    }

    private fun observeNowPlaying(){
        viewModelScope.launch (Dispatchers.IO){
            _nowPlayingMovies.emit(
                nowPlayingDao.observeMovies()
            )
        }
        getNowPlaying()
    }

    private fun observePopularMovies(){

        viewModelScope.launch (Dispatchers.IO){
            _popularMovies.emit(
                popularMovieDao.observeMovies()
            )
        }
        getPopular()
    }

    private fun observeTopMovies(){

        viewModelScope.launch(Dispatchers.IO){
            _topMovies.emit(
                topMovieDao.observeMovies()
            )
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
                        it.results.forEach{movie ->
                            nowPlayingDao.addMovie(movie.toNowPlayingEntity())
                        }
                        observeNowPlaying()
                    }
                }
            ).collect(_result)
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getTopRated()
                },
                onDataReady = {
                    viewModelScope.launch(Dispatchers.IO) {
                        it.results.forEach{movie ->
                            popularMovieDao.addMovie(movie.toPopularMovieEntity())
                        }
                        observePopularMovies()
                    }
                }
            ).collect(_result)
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    homeApi.getMostPopular()
                },
                onDataReady = {
                    viewModelScope.launch(Dispatchers.IO) {
                        it.results.forEach{movie ->
                            topMovieDao.addMovie(movie.toTopPlayingEntity())
                        }
                        observeTopMovies()
                    }
                }
            ).collect(_result)
        }
    }
}