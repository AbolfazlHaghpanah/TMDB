package com.hooshang.tmdb.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Singleton
    @Provides
    fun provideDispatcher(): Dispatchers = Dispatchers
}