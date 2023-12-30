package com.hooshang.tmdb.feature.home.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.home.data.datasource.local.HomeLocalDataSource
import com.hooshang.tmdb.feature.home.data.datasource.local.HomeLocalDataSourceImpl
import com.hooshang.tmdb.feature.home.data.datasource.remote.HomeRemoteDataSource
import com.hooshang.tmdb.feature.home.data.datasource.remote.HomeRemoteDataSourceImpl
import com.hooshang.tmdb.feature.home.data.db.dao.HomeDao
import com.hooshang.tmdb.feature.home.data.network.HomeApi
import com.hooshang.tmdb.feature.home.data.repository.HomeRepositoryImpl
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindHomeLocalDataSource(impl: HomeLocalDataSourceImpl): HomeLocalDataSource

    @Binds
    abstract fun bindHomeRemoteDataSource(impl: HomeRemoteDataSourceImpl): HomeRemoteDataSource

    companion object {
        @Provides
        fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
            return appDatabase.MovieDao()
        }

        @Provides
        fun provideHomeApi(retrofit: Retrofit): HomeApi {
            return retrofit.create(HomeApi::class.java)
        }

        @Provides
        fun provideHomeDao(appDatabase: AppDatabase): HomeDao {
            return appDatabase.HomeDao()
        }
    }
}