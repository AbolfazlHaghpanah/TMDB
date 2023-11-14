package com.example.tmdb.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.detail.network.DetailApi
import com.example.tmdb.feature.detail.network.json.Genre
import com.example.tmdb.feature.detail.network.json.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailApi: DetailApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _movieDetail: MutableStateFlow<MovieDetail?> = MutableStateFlow(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieDetailResult = MutableStateFlow<Result>(Result.Idle)
    val movieDetailResult = _movieDetailResult.asStateFlow()

    private var _genres: MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
    val genres = _genres.asStateFlow()

    init {
        fetchMovieDetail()
        fetchAllGenres()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            safeApi(call = {
                detailApi.getMovieDetail()
            },
                onDataReady = {
                    _movieDetail.value = it
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
                    _genres.value = it
//                    _genres.value = it.filter { genre ->
//                        _movieDetail.value?.similar.results?.contains(genre) ?:
//                    }
                }
            )
        }
    }
}