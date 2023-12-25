package com.hooshang.tmdb.feature.favorite.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.R
import com.hooshang.tmdb.core.ui.BaseViewModel
import com.hooshang.tmdb.core.utils.SnackBarManager
import com.hooshang.tmdb.core.utils.SnackBarMassage
import com.hooshang.tmdb.core.utils.StringResWrapper
import com.hooshang.tmdb.core.utils.databaseErrorCatchMessage
import com.hooshang.tmdb.feature.favorite.domain.model.FavoriteMovieDomainModel
import com.hooshang.tmdb.feature.favorite.domain.use_case.ObserveFavoriteUseCase
import com.hooshang.tmdb.feature.favorite.ui.contracts.FavoriteActions
import com.hooshang.tmdb.feature.favorite.ui.contracts.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val observeFavoriteUseCase: ObserveFavoriteUseCase,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<FavoriteActions, FavoriteState>() {
    init {
        observeFavoriteMovies()
    }

    override fun setInitialState(): FavoriteState = FavoriteState()

    private fun observeFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            observeFavoriteUseCase()
                .distinctUntilChanged()
                .catch {
                    snackBarManager.sendMessage(
                        SnackBarMassage(
                            snackBarMessage = databaseErrorCatchMessage(it),
                            snackBarDuration = SnackbarDuration.Short,
                            snackBarAction = {
                                observeFavoriteMovies()
                            },
                            snackBarActionLabel = StringResWrapper(R.string.label_try_again)
                        )
                    )
                }.collect { domainModel ->
                    setState { domainModel.toFavoriteState() }
                }
        }
    }

    private fun List<FavoriteMovieDomainModel>.toFavoriteState(): FavoriteState {
        return FavoriteState(movies = this.toPersistentList())
    }
}
