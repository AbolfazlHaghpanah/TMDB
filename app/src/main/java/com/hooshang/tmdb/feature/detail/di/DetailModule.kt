package com.hooshang.tmdb.feature.detail.di

import com.hooshang.tmdb.core.data.AppDatabase
import com.hooshang.tmdb.feature.detail.data.db.dao.DetailDao
import com.hooshang.tmdb.feature.detail.data.network.api.DetailApi
import com.hooshang.tmdb.feature.detail.domain.repository.DetailRepository
import com.hooshang.tmdb.feature.detail.domain.usecase.AddFavoriteUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.DetailUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.FetchDetailUseCase
import com.hooshang.tmdb.feature.detail.domain.usecase.ObserveDetailUseCase
import com.hooshang.tmdb.feature.favorite.domain.use_case.FavoriteUseCase
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

    @Provides
    fun provideUseCase(
        detailRepository: DetailRepository,
        favoriteUseCase: FavoriteUseCase
    ): DetailUseCase {
        return DetailUseCase(
            addFavoriteUseCase = AddFavoriteUseCase(detailRepository),
            removeFavoriteUseCase = favoriteUseCase.deleteFromFavoriteUseCase,
            fetchDetailUseCase = FetchDetailUseCase(detailRepository),
            observeDetailUseCase = ObserveDetailUseCase(detailRepository)
        )
    }
}