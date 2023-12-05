package com.hooshang.tmdb.feature.detail.domain.usecase

data class DetailUseCase(
    val addFavoriteUseCase: AddFavoriteUseCase,
    val removeFavoriteUseCase: RemoveFavoriteUseCase,
    val fetchDetailUseCase: FetchDetail,
    val observeDetailUseCase: ObserveDetail
)
