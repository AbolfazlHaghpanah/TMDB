package com.hooshang.tmdb.feature.favorite.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.feature.favorite.data.source.local.dao.FavoriteMovieDao
import com.hooshang.tmdb.feature.favorite.data.source.local.localdatasource.FavoriteLocalDataSource
import com.hooshang.tmdb.feature.favorite.data.repository.FavoriteRepositoryImpl
import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import com.hooshang.tmdb.feature.favorite.domain.use_case.DeleteFromFavoriteUseCase
import com.hooshang.tmdb.feature.favorite.domain.use_case.FavoriteUseCase
import com.hooshang.tmdb.feature.favorite.domain.use_case.GetFavoriteUseCase
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