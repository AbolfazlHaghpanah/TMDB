package com.hooshang.tmdb.feature.favorite.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.feature.favorite.data.datasource.local.FavoriteLocalDataSource
import com.hooshang.tmdb.feature.favorite.data.datasource.local.FavoriteLocalDataSourceImpl
import com.hooshang.tmdb.feature.favorite.data.db.dao.FavoriteMovieDao
import com.hooshang.tmdb.feature.favorite.data.repository.FavoriteRepositoryImpl
import com.hooshang.tmdb.feature.favorite.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {
    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteLocalDataSource(impl: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
            return appDatabase.favoriteMovieDao()
        }

        @Singleton
        @Provides
        fun provideDispatcher(): CoroutineContext {
            return Dispatchers.IO
        }
    }
}