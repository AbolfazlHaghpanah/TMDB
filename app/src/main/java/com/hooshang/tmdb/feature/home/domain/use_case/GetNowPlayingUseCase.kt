package com.hooshang.tmdb.feature.home.domain.use_case

import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetNowPlayingUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Flow<List<HomeMovieDomainModel>> {
        return homeRepository.getNowPlaying()
    }
}