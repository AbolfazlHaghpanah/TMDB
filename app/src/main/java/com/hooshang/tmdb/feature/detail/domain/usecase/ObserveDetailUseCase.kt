package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObserveDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int): Flow<MovieDetailDomainModel> =
        detailRepository.observeMovieDetails(id).flowOn(coroutineDispatcher)
}