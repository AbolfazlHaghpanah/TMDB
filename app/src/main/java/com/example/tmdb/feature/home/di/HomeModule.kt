package com.example.tmdb.feature.home.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.feature.home.data.genre.GenreDao
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingDao
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieDao
import com.example.tmdb.feature.home.data.topmovies.TopMovieDao
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
    fun providePopularMoviesDao(appDatabase: AppDatabase):PopularMovieDao{
        return appDatabase.popularMovieDao()
    }

    @Singleton
    @Provides
    fun provideNowPlayingDao(appDatabase: AppDatabase):NowPlayingDao{
        return appDatabase.NowPlayingDao()
    }

    @Singleton
    @Provides
    fun provideTopMovieDao(appDatabase: AppDatabase):TopMovieDao{
        return appDatabase.TopMovieDao()
    }

    @Singleton
    @Provides
    fun provideGenreDao(appDatabase: AppDatabase):GenreDao{
        return appDatabase.genreDao()
    }

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit):HomeApi{
        return retrofit.create(HomeApi::class.java)
    }
}