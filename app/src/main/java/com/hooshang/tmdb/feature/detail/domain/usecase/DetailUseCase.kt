package com.hooshang.tmdb.feature.detail.domain.usecase

import com.hooshang.tmdb.feature.favorite.domain.use_case.DeleteFromFavoriteUseCase

data class DetailUseCase(
    val addFavoriteUseCase: AddFavoriteUseCase,
    val removeFavoriteUseCase: DeleteFromFavoriteUseCase,
    val fetchDetailUseCase: FetchDetailUseCase,
    val observeDetailUseCase: ObserveDetailUseCase
)
