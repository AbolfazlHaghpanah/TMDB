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
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Singleton
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Singleton
    @Binds
    abstract fun bindHomeLocalDataSource(impl: HomeLocalDataSourceImpl): HomeLocalDataSource

    @Singleton
    @Binds
    abstract fun bindHomeRemoteDataSource(impl: HomeRemoteDataSourceImpl): HomeRemoteDataSource

    companion object {
        @Singleton
        @Provides
        fun provideTopMovieDao(appDatabase: AppDatabase): MovieDao {
            return appDatabase.MovieDao()
        }

        @Singleton
        @Provides
        fun provideHomeApi(retrofit: Retrofit): HomeApi {
            return retrofit.create(HomeApi::class.java)
        }

        @Singleton
        @Provides
        fun provideHomeDao(appDatabase: AppDatabase): HomeDao {
            return appDatabase.HomeDao()
        }

        @Singleton
        @Provides
        fun provideDispatcher(): CoroutineContext {
            return Dispatchers.IO
        }
    }
}