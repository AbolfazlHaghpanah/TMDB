package com.example.tmdb.feature.detail.domain.repository

import com.example.tmdb.feature.detail.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun addToFavorite(movieId: Int, genres: List<Int>)
    suspend fun removeFromFavorite(movieDetail: MovieDetail)
    suspend fun observeDetailMovieWithAllRelations(id: Int): Flow<MovieDetail>
    suspend fun fetchMovieDetail(id: Int)
}