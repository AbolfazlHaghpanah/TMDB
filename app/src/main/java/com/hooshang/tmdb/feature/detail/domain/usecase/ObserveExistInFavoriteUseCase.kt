package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObserveExistInFavoriteUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke(id : Int): Flow<Boolean> =
        repository.observeExistInFavorite(id).flowOn(dispatchers.IO)
}