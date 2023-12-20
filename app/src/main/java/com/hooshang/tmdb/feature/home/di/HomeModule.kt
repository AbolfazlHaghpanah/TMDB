package com.hooshang.tmdb.feature.home.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.home.data.db.HomeDao
import com.hooshang.tmdb.feature.home.data.datasource.local.HomeLocalDataSourceImpl
import com.hooshang.tmdb.feature.home.data.network.HomeApi
import com.hooshang.tmdb.feature.home.data.datasource.remote.HomeRemoteDataSourceImpl
import com.hooshang.tmdb.feature.home.data.repository.HomeRepositoryImpl
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import com.hooshang.tmdb.feature.home.domain.use_case.FetchGenresUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchNowPlayingUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchPopularMovieUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchTopMovieUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.ObserveNowPlayingUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.ObservePopularUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.ObserveTopUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

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
    fun provideHomeRepository(
        homeLocalDataSourceImpl: HomeLocalDataSourceImpl,
        homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl
    ): HomeRepository {
        return HomeRepositoryImpl(
            homeLocalDataSourceImpl,
            homeRemoteDataSourceImpl
        )
    }

    @Singleton
    @Provides
    fun provideUseCase(homeRepository: HomeRepository): HomeUseCase {
        return HomeUseCase(
            fetchGenresUseCase = FetchGenresUseCase(homeRepository),
            fetchNowPlayingUseCase = FetchNowPlayingUseCase(homeRepository),
            fetchPopularMovieUseCase = FetchPopularMovieUseCase(homeRepository),
            fetchTopMovieUseCase = FetchTopMovieUseCase(homeRepository),
            observeNowPlayingUseCase = ObserveNowPlayingUseCase(homeRepository),
            observePopularUseCase = ObservePopularUseCase(homeRepository),
            observeTopUseCase = ObserveTopUseCase(homeRepository),
        )
    }
}