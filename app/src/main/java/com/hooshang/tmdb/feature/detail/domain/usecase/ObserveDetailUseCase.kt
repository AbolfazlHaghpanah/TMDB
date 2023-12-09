package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow

class ObserveDetailUseCase(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: Int): Flow<MovieDetailDomainModel> {
        return detailRepository.observeDetailMovieWithAllRelations(id)
    }
}