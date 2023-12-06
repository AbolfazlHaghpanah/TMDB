package com.hooshang.tmdb.feature.favorite.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.databaseErrorCatchMessage
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import com.hooshang.tmdb.feature.favorite.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<FavoriteActions, FavoriteState, FavoriteEffect>() {

    init {
        observeFavoriteMovies()
    }

    override fun onAction(action: FavoriteActions) {
        when (action) {
            is FavoriteActions.TryAgain -> onTryAgain()
            else -> {}
        }
    }

    override fun setInitialState(): FavoriteState {
        return FavoriteState()
    }

    private fun onTryAgain() {
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
                }.collect { domainModel ->
                    setState { domainModel.toFavoriteState() }
                }
        }
    }

    private fun List<FavoriteMovieDomainModel>.toFavoriteState(): FavoriteState {
        return FavoriteState(
            this.map {
                FavoriteMovie(
                    id = it.id,
                    backdropPath = it.backdropPath,
                    voteAverage = it.voteAverage,
                    genres = it.genres,
                    title = it.title
                )
            }.toPersistentList()
        )
    }
}
