package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository

class FetchDetailUseCase(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: Int) {
        detailRepository.fetchMovieDetail(id)
    }
}