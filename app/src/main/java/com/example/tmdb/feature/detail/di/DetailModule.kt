package com.example.tmdb.feature.detail.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.feature.detail.data.CreditDao
import com.example.tmdb.feature.detail.data.MovieDao
import com.example.tmdb.feature.detail.network.DetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Singleton
    @Provides
    fun provideDetailApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCreditDao(appDatabase: AppDatabase): CreditDao {
        return appDatabase.CreditDao()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.MovieDao()
    }

}