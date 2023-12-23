package com.hooshang.tmdb.feature.home.domain.use_case

import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FetchNowPlayingUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val dispatcher: CoroutineContext
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        homeRepository.fetchNowPlaying()
    }

}