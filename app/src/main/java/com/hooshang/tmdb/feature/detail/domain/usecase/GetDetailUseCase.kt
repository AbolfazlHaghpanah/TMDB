package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke(id: Int): MovieDetailDomainModel = withContext(dispatchers.IO){
        repository.getMovieDetails(id)
    }
}