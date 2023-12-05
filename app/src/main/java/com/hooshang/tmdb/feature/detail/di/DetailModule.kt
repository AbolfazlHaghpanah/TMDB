package com.hooshang.tmdb.feature.detail.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.feature.detail.data.source.local.dao.DetailDao
import com.hooshang.tmdb.feature.detail.data.source.remote.api.DetailApi
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import com.hooshang.tmdb.feature.detail.domain.usecase.AddFavoriteUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.DetailUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.FetchDetail
import com.hooshang.tmdb.feature.detail.domain.usecase.ObserveDetail
import com.hooshang.tmdb.feature.detail.domain.usecase.RemoveFavoriteUseCase
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
    fun provideDetailDao(appDatabase: AppDatabase): DetailDao {
        return appDatabase.DetailDao()
    }

    @Singleton
    @Provides
    fun provideUseCase(detailRepository: DetailRepository): DetailUseCase {
        return DetailUseCase(
            addFavoriteUseCase = AddFavoriteUseCase(detailRepository),
            removeFavoriteUseCase = RemoveFavoriteUseCase(detailRepository),
            fetchDetailUseCase = FetchDetail(detailRepository),
            observeDetailUseCase = ObserveDetail(detailRepository)
        )
    }
}