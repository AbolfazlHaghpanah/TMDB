package com.example.tmdb.feature.favorite.domain.use_case

import com.example.tmdb.feature.favorite.domain.repository.FavoriteRepository

class DeleteFromFavoriteUseCase (
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id : Int){
        favoriteRepository.deleteFromFavorite(id)
    }
}