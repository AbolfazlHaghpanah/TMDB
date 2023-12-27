package com.hooshang.tmdb.feature.detail.domain.repository

import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetails(id: Int): MovieDetailDomainModel
    suspend fun fetchMovieDetail(id: Int)
    suspend fun addToFavoriteWithGenre(movieId: Int, genres: List<Int>)
    suspend fun observeExistInFavorite(id: Int): Flow<Boolean>
}