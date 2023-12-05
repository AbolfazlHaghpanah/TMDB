package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository

class RemoveFavoriteUseCase(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(movieDetailDomainModel: MovieDetailDomainModel) {
        detailRepository.removeFromFavorite(movieDetailDomainModel)
    }
}