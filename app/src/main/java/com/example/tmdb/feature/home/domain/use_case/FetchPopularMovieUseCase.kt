package com.example.tmdb.feature.home.domain.use_case

import com.example.tmdb.feature.home.domain.repository.HomeRepository

class FetchPopularMovieUseCase (
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(){
        homeRepository.fetchPopularMovie()
    }
}