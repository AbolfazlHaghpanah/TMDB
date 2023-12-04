package com.example.tmdb.feature.detail.domain.usecase

import com.example.tmdb.feature.detail.domain.repository.DetailRepository

class AddFavoriteUseCase(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(movieId: Int, genres: List<Int>) {
        detailRepository.addToFavorite(movieId, genres)
    }

}