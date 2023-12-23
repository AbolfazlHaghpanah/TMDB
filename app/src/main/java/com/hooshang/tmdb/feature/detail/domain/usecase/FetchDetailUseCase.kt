package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FetchDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend operator fun invoke(id: Int) = withContext(coroutineContext){
        detailRepository.fetchMovieDetail(id)
    }
}