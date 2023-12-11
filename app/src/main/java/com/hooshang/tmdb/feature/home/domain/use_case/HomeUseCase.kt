package com.hooshang.tmdb.feature.home.domain.use_case

data class HomeUseCase(
    val fetchGenresUseCase: FetchGenresUseCase,
    val fetchPopularMovieUseCase: FetchPopularMovieUseCase,
    val fetchNowPlayingUseCase: FetchNowPlayingUseCase,
    val fetchTopMovieUseCase: FetchTopMovieUseCase,
    val observeNowPlayingUseCase: ObserveNowPlayingUseCase,
    val observePopularUseCase: ObservePopularUseCase,
    val observeTopUseCase: ObserveTopUseCase
)
