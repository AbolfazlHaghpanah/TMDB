package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.model.MovieDetailDomainModel
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetDetailUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend operator fun invoke(id: Int): MovieDetailDomainModel = withContext(coroutineContext){
        repository.getMovieDetails(id)
    }
}