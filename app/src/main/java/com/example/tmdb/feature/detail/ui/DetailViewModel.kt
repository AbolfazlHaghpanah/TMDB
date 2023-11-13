package com.example.tmdb.feature.detail.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.feature.detail.network.DetailApi
import com.example.tmdb.feature.detail.network.json.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailApi: DetailApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _movieDetail: MutableSharedFlow<MovieDetail> = MutableSharedFlow<MovieDetail>()
    val movieDetail = _movieDetail.asSharedFlow()

    private val _movieDetailResult = MutableStateFlow<Result>(Result.Idle)
    val movieDetailResult = _movieDetailResult.asStateFlow()

    init {
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            Log.d("TAG", "fetchMovieDetail: ${detailApi.getMovieDetail().body()?.original_title?:"mmd"}")
            safeApi(call = {
                detailApi.getMovieDetail()
            },
                onDataReady = {
                    Log.d("test", "${it.original_title}")
//                    _movieDetail =
//                        MutableSharedFlow(it)
                }
            ).collect(_movieDetailResult)
        }
    }
}