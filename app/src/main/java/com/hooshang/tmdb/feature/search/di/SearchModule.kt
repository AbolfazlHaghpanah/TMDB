package com.hooshang.tmdb.feature.search.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.feature.search.data.db.dao.SearchDao
import com.hooshang.tmdb.feature.search.data.datasource.localdatasource.SearchLocalDataSource
import com.hooshang.tmdb.feature.search.data.network.api.SearchApi
import com.hooshang.tmdb.feature.search.data.datasource.remotedatasource.SearchRemoteDataSource
import com.hooshang.tmdb.feature.search.data.repository.SearchRepositoryImpl
import com.hooshang.tmdb.feature.search.domain.repository.SearchRepository
import com.hooshang.tmdb.feature.search.domain.use_case.SearchUseCase
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