package com.example.tmdb.feature.home.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.moviedata.Dao.MovieDao
import com.example.tmdb.feature.home.data.nowplayingmovie.dao.NowPlayingDao
import com.example.tmdb.feature.home.data.popularMovie.dao.PopularMovieDao
import com.example.tmdb.feature.home.network.HomeApi
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
    fun provideGenreDao(appDatabase: AppDatabase): GenreDao {
        return appDatabase.genreDao()
    }

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNowPlayingDao(appDatabase: AppDatabase): NowPlayingDao {
        return appDatabase.NowPlayingDao()
    }

    @Singleton
    @Provides
    fun providePopularMovieDao(appDatabase: AppDatabase): PopularMovieDao {
        return appDatabase.PopularMovieDao()
    }
}