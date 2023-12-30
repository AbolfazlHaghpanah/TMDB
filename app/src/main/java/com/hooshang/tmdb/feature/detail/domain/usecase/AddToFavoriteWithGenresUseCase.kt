package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddToFavoriteWithGenresUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke(movieId: Int, genres: List<Int>) = withContext(dispatchers.IO) {
        repository.addToFavoriteWithGenres(movieId, genres)
    }
}