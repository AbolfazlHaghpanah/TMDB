package com.example.tmdb.core.ui.bottomsheet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.data.databaseErrorCatchMessage
import com.example.tmdb.core.utils.SnackBarMessage
import com.example.tmdb.feature.favorite.data.FavoriteMovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBModalBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val snackBarMessage: SnackBarMessage
) : ViewModel() {

    private val movieId = savedStateHandle.get<String>("id")?.toIntOrNull() ?: -1

    fun deleteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteMovieDao.deleteMovie(movieId)
            } catch (t: Throwable) {
                snackBarMessage.sendMessage(databaseErrorCatchMessage(t))
            }
        }
    }
}