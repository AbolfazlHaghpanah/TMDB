package com.example.tmdb.feature.home.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.feature.home.data.genre.dao.GenreDao
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
        return appDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideGenreDao(appDatabase: AppDatabase): GenreDao {
        return appDatabase.genreDao()
    }

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit):HomeApi{
        return retrofit.create(HomeApi::class.java)
    }
}