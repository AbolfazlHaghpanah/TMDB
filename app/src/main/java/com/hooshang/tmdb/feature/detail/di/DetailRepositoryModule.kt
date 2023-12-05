package com.hooshang.tmdb.feature.detail.di

import com.hooshang.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
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