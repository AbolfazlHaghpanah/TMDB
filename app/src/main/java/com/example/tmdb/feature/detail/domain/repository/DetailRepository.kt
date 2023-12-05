package com.example.tmdb.feature.detail.domain.repository

import com.example.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun addToFavorite(movieId: Int, genres: List<Int>)
    suspend fun removeFromFavorite(movieDetailDomainModel: MovieDetailDomainModel)
    suspend fun observeDetailMovieWithAllRelations(id: Int): Flow<MovieDetailDomainModel>
    suspend fun fetchMovieDetail(id: Int)
}