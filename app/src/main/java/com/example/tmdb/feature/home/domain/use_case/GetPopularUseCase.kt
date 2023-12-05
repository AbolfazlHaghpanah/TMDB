package com.example.tmdb.feature.home.domain.use_case

import com.example.tmdb.feature.home.domain.model.HomeMovieDomainModel
import com.example.tmdb.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetPopularUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() : Flow<List<HomeMovieDomainModel>> {
        return homeRepository.getPopularMovie()
    }
}