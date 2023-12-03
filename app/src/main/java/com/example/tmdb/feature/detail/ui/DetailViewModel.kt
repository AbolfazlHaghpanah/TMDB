package com.example.tmdb.feature.detail.ui

import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.core.utils.databaseErrorCatchMessage
import com.example.tmdb.feature.favorite.data.entity.FavoriteMovieEntity
import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.core.network.Result
import com.example.tmdb.core.network.safeApi
import com.example.tmdb.core.utils.SnackBarManager
import com.example.tmdb.core.utils.SnackBarMassage
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.source.local.dao.DetailDao
import com.example.tmdb.feature.detail.data.source.local.relation.DetailMovieWithAllRelations
import com.example.tmdb.feature.detail.data.source.remote.DetailApi
import com.example.tmdb.feature.detail.data.source.remote.dto.MovieDetailDto
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    private val id: Int = savedStateHandle.get<String>("id")?.toInt() ?: 0

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<SnackBarMassage?>(null)

    init {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
        }
        observeDetailMovieWithAllRelations()
    }

    fun addToFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDetail.value?.genres?.forEach {
                    detailDao.addFavoriteMovieGenre(
                        FavoriteMovieGenreCrossRef(
                            id,
                            it.genreId
                        )
                    )
                }
                detailDao.addToFavorite(FavoriteMovieEntity(id))
            } catch (t: Throwable) {
                sendDataBaseError(throwable = t, onTryAgain = {
                    addToFavorite()
                })
            }
        }
    }

    fun removeFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDetail.value?.genres?.forEach { genre ->
                    detailDao.deleteFavoriteMovieGenre(
                        FavoriteMovieGenreCrossRef(
                            genreId = genre.genreId,
                            movieId = id
                        )
                    )
                }

                detailDao.deleteFavorite(
                    FavoriteMovieEntity(
                        movieId = id
                    )
                )

            } catch (t: Throwable) {
                sendDataBaseError(throwable = t, onTryAgain = {
                    removeFromFavorite()
                })
            }
        }
    }

    private fun observeDetailMovieWithAllRelations() {
        viewModelScope.launch(Dispatchers.IO) {
            detailDao.observeMovieDetail(id)
                .catch {
                    sendDataBaseError(throwable = it, onTryAgain = {
                        observeDetailMovieWithAllRelations()
                    })
                }
                .collect {
                    _movieDetail.emit(it)
                }
        }
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            snackBarManager.dismissSnackBar()
            safeApi(call = {
                detailApi.getMovieDetail(id = id)
            }
            ).collect {
                addMovieDetail(it)
            }
        }
    }

    private suspend fun addMovieDetail(result: Result) {
        when (result) {
            is Result.Success<*> -> {
                _isLoading.emit(false)
                val data =
                    result.response as MovieDetailDto

                addMovieDetailEntity(data)
            }

            is Result.Error -> {
                _isLoading.emit(false)
                val error = result.message
                _snackBarMessage.emit(
                    SnackBarMassage(
                        snackBarMessage = error,
                        isHaveToShow = true,
                        snackBarAction = {
                            fetchMovieDetail()
                        },
                        snackBarActionLabel = "try again"
                    )
                )
                snackBarManager.sendMessage(_snackBarMessage.value)
            }

            else -> {}
        }
    }


    suspend fun showLastSnackBar() {
        snackBarManager.sendMessage(
            _snackBarMessage.value
        )
    }

    private suspend fun sendDataBaseError(
        throwable: Throwable,
        onTryAgain: () -> Unit
    ) {
        _snackBarMessage.emit(
            SnackBarMassage(
                snackBarMessage = databaseErrorCatchMessage(throwable),
                snackBarActionLabel = "Try Again",
                snackBarAction = onTryAgain,
                snackBarDuration = SnackbarDuration.Short
            )
        )
        snackBarManager.sendMessage(
            snackBarMassage = _snackBarMessage.value
        )
    }

    private fun addMovieDetailEntity(movieDetailDto: MovieDetailDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDao.addMovie(
                    MovieEntity(
                        id = movieDetailDto.id,
                        posterPath = movieDetailDto.posterPath,
                        voteAverage = movieDetailDto.voteAverage.toDouble(),
                        backdropPath = "",
                        title = movieDetailDto.title
                    )
                )

                detailDao.addDetail(movieDetailDto.toDetailEntity())

                detailDao.addCredits(movieDetailDto.toCreditsEntity())

                movieDetailDto.credits.cast.forEach {
                    detailDao.addDetailMovieWithCreditCrossRef(
                        DetailMovieWithCreditCrossRef(
                            detailMovieId = movieDetailDto.id,
                            creditId = it.id
                        )
                    )
                }
                movieDetailDto.credits.crew.forEach {
                    detailDao.addDetailMovieWithCreditCrossRef(
                        DetailMovieWithCreditCrossRef(
                            detailMovieId = movieDetailDto.id,
                            creditId = it.id
                        )
                    )
                }

                movieDetailDto.genreDtos.forEach {
                    detailDao.addDetailMovieWithGenreCrossRef(
                        DetailMovieWithGenreCrossRef(
                            detailMovieId = movieDetailDto.id,
                            genreId = it.id
                        )
                    )
                }

                movieDetailDto.similar.results.forEach {
                    detailDao.addDetailMovieWithSimilarMoviesCrossRef(
                        DetailMovieWithSimilarMoviesCrossRef(
                            detailMovieId = movieDetailDto.id,
                            id = it.id
                        )
                    )
                    movieDao.addMovie(
                        MovieEntity(
                            id = it.id,
                            title = it.title,
                            backdropPath = "",
                            voteAverage = it.voteAverage.toDouble(),
                            posterPath = it.posterPath ?: ""
                        )
                    )
                }

                movieDetailDto.similar.results.forEach { similarMovieResult ->
                    similarMovieResult.genreIds.forEach { genreId ->
                        detailDao.addMovieWithGenreCrossRef(
                            MovieWithGenreCrossRef(
                                id = similarMovieResult.id,
                                genreId = genreId
                            )
                        )
                    }
                }

            } catch (t: Throwable) {
                sendDataBaseError(throwable = t, onTryAgain = {
                    addMovieDetailEntity(movieDetailDto)
                })
            }
        }
    }
}
