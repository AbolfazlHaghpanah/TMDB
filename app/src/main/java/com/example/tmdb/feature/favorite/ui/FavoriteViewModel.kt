package com.example.tmdb.feature.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.feature.favorite.data.FavoriteMovieDao
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : ViewModel() {
    private val _favoriteMovieList =
        MutableStateFlow<List<MovieWithGenreDatabaseWrapper>>(emptyList())
    val favoriteMovieList = _favoriteMovieList.asStateFlow()

    init {
        observeFavoriteMovies()
    }

    private fun observeFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieDao.observeMovies().collect {
                _favoriteMovieList.emit(it.map { movie -> movie.toMovieDatabaseWrapper() })
            }
        }
    }
}