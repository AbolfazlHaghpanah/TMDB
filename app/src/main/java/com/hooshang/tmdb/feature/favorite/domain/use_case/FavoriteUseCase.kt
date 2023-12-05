package com.hooshang.tmdb.feature.favorite.domain.use_case

data class FavoriteUseCase(
    val getFavoriteUseCase: GetFavoriteUseCase,
    val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase
)
