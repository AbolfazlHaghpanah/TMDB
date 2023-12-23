package com.hooshang.tmdb.feature.home.domain.use_case

import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ObserveNowPlayingUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val dispatcher: CoroutineContext
) {
    suspend operator fun invoke(): Flow<List<HomeMovieDomainModel>> = withContext(dispatcher) {
        homeRepository.getNowPlaying()
    }
}