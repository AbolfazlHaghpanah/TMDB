package com.example.tmdb.feature.detail.domain.usecase

import com.example.tmdb.feature.detail.domain.model.MovieDetail
import com.example.tmdb.feature.detail.domain.repository.DetailRepository

class RemoveFavoriteUseCase(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(movieDetail: MovieDetail) {
        detailRepository.removeFromFavorite(movieDetail)
    }
}