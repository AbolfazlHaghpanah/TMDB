package com.example.tmdb.feature.detail.di

import com.example.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.example.tmdb.feature.detail.domain.repository.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailRepositoryModule {
    @Binds
    abstract fun provideDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository
}