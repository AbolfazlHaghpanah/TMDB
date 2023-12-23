package com.hooshang.tmdb.feature.detail.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.feature.detail.data.datasource.localdatasource.DetailLocalDataSource
import com.hooshang.tmdb.feature.detail.data.datasource.localdatasource.DetailLocalDataSourceImpl
import com.hooshang.tmdb.feature.detail.data.datasource.remotedatasource.DetailRemoteDataSource
import com.hooshang.tmdb.feature.detail.data.datasource.remotedatasource.DetailRemoteDataSourceImpl
import com.hooshang.tmdb.feature.detail.data.db.dao.DetailDao
import com.hooshang.tmdb.feature.detail.data.network.DetailApi
import com.hooshang.tmdb.feature.detail.data.repository.DetailRepositoryImpl
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailModule {
    @Binds
    abstract fun bindsDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository

    @Binds
    abstract fun bindsDetailLocalDataSource(
        detailLocalDataSourceImpl: DetailLocalDataSourceImpl
    ): DetailLocalDataSource

    @Binds
    abstract fun bindsDetailRemoteDataSource(
        detailRemoteDataSourceImpl: DetailRemoteDataSourceImpl
    ): DetailRemoteDataSource

    companion object {
        @Singleton
        @Provides
        fun provideDetailApi(retrofit: Retrofit): DetailApi {
            return retrofit.create(DetailApi::class.java)
        }

        @Singleton
        @Provides
        fun provideDetailDao(appDatabase: AppDatabase): DetailDao {
            return appDatabase.DetailDao()
        }
    }
}