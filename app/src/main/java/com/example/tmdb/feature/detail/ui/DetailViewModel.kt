package com.example.tmdb.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.feature.detail.data.detail.DetailDao
import com.example.tmdb.feature.detail.data.relation.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.network.DetailApi
import com.example.tmdb.feature.detail.network.json.MovieDetail
import com.example.tmdb.feature.favorite.data.FavoriteMovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailApi: DetailApi,
    private val detailDao: DetailDao,
    private val movieDao: MovieDao,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private var _movieDetail: MutableStateFlow<DetailMovieWithAllRelations?> =
        MutableStateFlow(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieDetailResult = MutableStateFlow<Result>(Result.Idle)

    val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0

    init {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
        }
        observeDetailMovieWithAllRelations()
    }

    fun addToFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.addToFavorite(
                FavoriteMovieEntity(id),
                movieDetail.value?.genres ?: listOf()
            )
        }
    }

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            detailDao.observeMovieDetail(id).collect {
                _movieDetail.emit(it)
            }
        }
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            safeApi(call = {
                detailApi.getMovieDetail(id = id)
            },
                onRequestDone = {
                    addMovieDetail()
                }
            ).collect(_movieDetailResult)
        }
    }

    private fun addMovieDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            when (_movieDetailResult.value) {
                is Result.Success<*> -> {
                    val data =
                        (_movieDetailResult.value as Result.Success<*>).response as MovieDetail

                    movieDao.addMovieDetail(data)
                }

                is Result.Error -> {
                    val error = (_movieDetailResult.value as Result.Error).message
                }

                else -> {}
            }
        }
    }
}