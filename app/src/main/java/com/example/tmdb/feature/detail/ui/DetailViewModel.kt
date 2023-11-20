package com.example.tmdb.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.detail.data.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.data.detail.DetailDao
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
    private val detailDao: DetailDao,
    private val movieDao: MovieDao
) : ViewModel() {

    private var _movieDetail: MutableStateFlow<DetailMovieWithAllRelations?> =
        MutableStateFlow(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieDetailResult = MutableStateFlow<Result>(Result.Idle)
    val movieDetailResult = _movieDetailResult.asStateFlow()

    init {
        observeDetailMovieWithAllRelations()
    }

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            detailDao.observeMovieDetail(550).collect {
                _movieDetail.emit(it)
            }
        }
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            safeApi(call = {
                detailApi.getMovieDetail()
            },
                onDataReady = {
                    viewModelScope.launch(Dispatchers.IO) {
                        movieDao.addMovieDetail(it)
                    }
                }
            ).collect(_movieDetailResult)
        }
    }

}