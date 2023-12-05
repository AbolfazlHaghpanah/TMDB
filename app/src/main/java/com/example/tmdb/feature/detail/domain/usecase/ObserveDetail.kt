package com.example.tmdb.feature.detail.domain.usecase

import com.example.tmdb.feature.detail.domain.model.MovieDetail
import com.example.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow

class ObserveDetail(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: Int): Flow<MovieDetail> {
        return detailRepository.observeDetailMovieWithAllRelations(id)
    }
}