package com.example.tmdb.feature.bottomsheet.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.databaseErrorCatchMessage
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.feature.favorite.data.dao.FavoriteMovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBModalBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val movieId = savedStateHandle.get<String>("id")?.toIntOrNull() ?: -1

    fun deleteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteMovieDao.deleteMovie(movieId)
            } catch (t: Throwable) {
                snackBarManager.sendMessage(SnackBarMassage(databaseErrorCatchMessage(t)))
            }
        }
    }
}