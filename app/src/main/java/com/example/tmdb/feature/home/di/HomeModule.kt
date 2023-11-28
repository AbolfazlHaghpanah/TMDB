package com.example.tmdb.feature.home.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.feature.home.data.dao.HomeDao
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
    fun provideHomeDao(appDatabase: AppDatabase): HomeDao {
        return appDatabase.HomeDao()
    }
}