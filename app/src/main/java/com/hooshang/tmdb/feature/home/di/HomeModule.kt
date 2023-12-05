package com.hooshang.tmdb.feature.home.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.feature.home.data.source.local.dao.HomeDao
import com.hooshang.tmdb.feature.home.data.source.local.localdatasource.HomeLocalDataSource
import com.hooshang.tmdb.feature.home.data.source.remote.api.HomeApi
import com.hooshang.tmdb.feature.home.data.source.remote.remotedatasource.HomeRemoteDataSource
import com.hooshang.tmdb.feature.home.data.repository.HomeRepositoryImpl
import com.hooshang.tmdb.feature.home.domain.repository.HomeRepository
import com.hooshang.tmdb.feature.home.domain.use_case.FetchGenresUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchNowPlayingUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchPopularMovieUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.FetchTopMovieUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.GetNowPlayingUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.GetPopularUseCase
import com.hooshang.tmdb.feature.home.domain.use_case.GetTopUseCase
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
        homeLocalDataSource: HomeLocalDataSource,
        homeRemoteDataSource: HomeRemoteDataSource
    ): HomeRepository {
        return HomeRepositoryImpl(
            homeLocalDataSource,
            homeRemoteDataSource
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
            getNowPlayingUseCase = GetNowPlayingUseCase(homeRepository),
            getPopularUseCase = GetPopularUseCase(homeRepository),
            getTopUseCase = GetTopUseCase(homeRepository),
        )
    }
}