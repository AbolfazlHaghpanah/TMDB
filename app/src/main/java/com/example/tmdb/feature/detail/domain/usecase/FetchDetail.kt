package com.example.tmdb.feature.detail.domain.usecase

import com.example.tmdb.feature.detail.domain.repository.DetailRepository

class FetchDetail(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: Int) {
        detailRepository.fetchMovieDetail(id)
    }
}