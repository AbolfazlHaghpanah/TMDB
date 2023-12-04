package com.example.tmdb.feature.favorite.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.feature.favorite.data.local.dao.FavoriteMovieDao
import com.example.tmdb.feature.favorite.data.local.localdatasource.FavoriteLocalDataSource
import com.example.tmdb.feature.favorite.data.repository.FavoriteRepositoryImpl
import com.example.tmdb.feature.favorite.domain.repository.FavoriteRepository
import com.example.tmdb.feature.favorite.domain.use_case.DeleteFromFavoriteUseCase
import com.example.tmdb.feature.favorite.domain.use_case.FavoriteUseCase
import com.example.tmdb.feature.favorite.domain.use_case.GetFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        localDataSource: FavoriteLocalDataSource
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun provideFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ): FavoriteUseCase {
        return FavoriteUseCase(
            GetFavoriteUseCase(favoriteRepository),
            DeleteFromFavoriteUseCase(favoriteRepository)
        )
    }
}