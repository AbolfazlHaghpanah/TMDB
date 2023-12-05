package com.hooshang.tmdb.feature.favorite.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.databaseErrorCatchMessage
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import com.hooshang.tmdb.feature.favorite.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val _favoriteMovieList =
        MutableStateFlow<List<FavoriteMovieDomainModel>>(emptyList())

    val favoriteMovieList = _favoriteMovieList.asStateFlow()

    init {
        observeFavoriteMovies()
    }

    fun onTryAgain() {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
        }
        observeFavoriteMovies()
    }

    private fun observeFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.getFavoriteUseCase()
                .catch {
                    snackBarManager.sendMessage(
                        SnackBarMassage(
                            snackBarMessage = databaseErrorCatchMessage(it),
                            snackBarDuration = SnackbarDuration.Indefinite,
                            snackBarAction = {
                                onTryAgain()
                            },
                            snackBarActionLabel = "Try Again"
                        )
                    )
                }.collect {
                    _favoriteMovieList.emit(it)
                }
        }
    }
}