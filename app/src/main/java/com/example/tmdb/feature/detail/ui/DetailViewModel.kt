package com.example.tmdb.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.detail.data.CreditDao
import com.example.tmdb.feature.detail.data.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.data.MovieDao
import com.example.tmdb.feature.detail.network.DetailApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailApi: DetailApi,
    savedStateHandle: SavedStateHandle,
    private val movieDao: MovieDao,
    private val creditDao: CreditDao
) : ViewModel() {

    private var _movieDetail: MutableStateFlow<DetailMovieWithAllRelations?> =
        MutableStateFlow(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieDetailResult = MutableStateFlow<Result>(Result.Idle)
    val movieDetailResult = _movieDetailResult.asStateFlow()

//    private var _genres: MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
//    val genres = _genres.asStateFlow()

    init {
        observeDetailMovieWithAllRelations()
        fetchMovieDetail()
        fetchAllGenres()
    }


    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.getDetailMovieWithAllRelations(550).collect {
                _movieDetail.value = it
            }
        }
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            safeApi(call = {
                detailApi.getMovieDetail()
            },
                onDataReady = {
//                    movieDao.addDetailMovie(it)
                }
            ).collect(_movieDetailResult)
        }
    }

    private fun fetchAllGenres() {
        viewModelScope.launch {
            safeApi(
                call = {
                    detailApi.getAllGenres()
                },
                onDataReady = {
//                    _genres.value = it
                }
            )
        }
    }
}