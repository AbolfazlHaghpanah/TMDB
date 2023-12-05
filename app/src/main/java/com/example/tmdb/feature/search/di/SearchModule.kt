package com.example.tmdb.feature.search.di

import com.example.tmdb.core.data.AppDatabase
import com.example.tmdb.feature.search.data.local.dao.SearchDao
import com.example.tmdb.feature.search.data.local.localdatasource.SearchLocalDataSource
import com.example.tmdb.feature.search.data.remote.SearchApi
import com.example.tmdb.feature.search.data.remote.remotedatasource.SearchRemoteDataSource
import com.example.tmdb.feature.search.data.repository.SearchRepositoryImpl
import com.example.tmdb.feature.search.domain.repository.SearchRepository
import com.example.tmdb.feature.search.domain.use_case.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchDao(appDatabase: AppDatabase): SearchDao {
        return appDatabase.SearchDao()
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchLocalDataSource: SearchLocalDataSource,
        searchRemoteDataSource: SearchRemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(
            searchLocalDataSource = searchLocalDataSource,
            searchRemoteDataSource = searchRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideUseCase(
        searchRepository: SearchRepository
    ): SearchUseCase {
        return SearchUseCase(searchRepository)
    }
}