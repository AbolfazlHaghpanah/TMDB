package com.example.tmdb.feature.detail.di

import com.example.tmdb.feature.detail.domain.repository.DetailRepository
import com.example.tmdb.feature.detail.domain.usecase.AddFavoriteUseCase
import com.example.tmdb.feature.detail.domain.usecase.DetailUseCase
import com.example.tmdb.feature.detail.domain.usecase.FetchDetail
import com.example.tmdb.feature.detail.domain.usecase.ObserveDetail
import com.example.tmdb.feature.detail.domain.usecase.RemoveFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DetailUseCaseModule {
    @Singleton
    @Provides
    fun provideUseCase(detailRepository: DetailRepository): DetailUseCase {
        return DetailUseCase(
            addFavoriteUseCase = AddFavoriteUseCase(detailRepository),
            removeFavoriteUseCase = RemoveFavoriteUseCase(detailRepository),
            fetchDetailUseCase = FetchDetail(detailRepository),
            observeDetailUseCase = ObserveDetail(detailRepository)
        )
    }

}